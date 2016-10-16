package org.mokai.web.admin.jogger.helpers;

import com.elibom.jogger.http.Response;
import static java.lang.StrictMath.log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mokai.Acceptor;
import org.mokai.Action;
import org.mokai.Connector;
import org.mokai.ConnectorService;
import org.mokai.ExposableConfiguration;
import org.mokai.Processor;
import org.mokai.ui.annotation.AcceptorsList;
import org.mokai.web.admin.jogger.Session;
import org.mokai.web.admin.jogger.controllers.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alejandro <lariverosc@gmail.com>
 */
public class WebUtil {

    private final static Logger log = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil() {
    }

    public static boolean isAuthenticated(Response response) {
        Session session = getSession(response);
        if (session != null && session.getUser() != null && !session.getUser().isEmpty()) {
            return true;
        }
        return false;
    }

    public static Session getSession(Response response) {
        Object session = response.getAttributes().get("session");
        if (session != null) {
            return (Session) session;
        }
        return null;
    }

    /**
     * Helper method. Converts a list of {@link ConnectorService} objects to a list of {@link ConnectorPresenter} objects.
     *
     * @param connectorServices the connector services that we want to convert into {@link ConnectorPresenter}
     *
     * @return a list of {@link ConnectorPresenter} objects.
     */
    public static List<ConnectorPresenter> buildConnectorUIs(List<ConnectorService> connectorServices) {
        List<ConnectorPresenter> connectorUIs = new ArrayList<ConnectorPresenter>();
        for (ConnectorService connectorService : connectorServices) {
            connectorUIs.add(new ConnectorPresenter(connectorService));
        }

        return connectorUIs;
    }

    /**
     * Helper method. Generates the JSON representation of a {@link ConnectorService} with configuration, acceptors
     * (if it is a {@link Processor}) and actions.
     *
     * @param connectorService the connector service from which we are generating the JSON representation.
     *
     * @return a JSONObject that holds the information of the connector service.
     * @throws JSONException
     */
    public static JSONObject getConnectorJSON(ConnectorService connectorService) throws JSONException {
        JSONObject jsonConnector = new ConnectorPresenter(connectorService).toJSON();

        // include configuration
        Connector connector = connectorService.getConnector();
        if (ExposableConfiguration.class.isInstance(connector)) {
            jsonConnector.put("configuration", getConfigJSON((ExposableConfiguration<?>) connector));
        }

        // include acceptors
        if (Processor.class.isInstance(connector)) {
            JSONArray jsonAcceptors = new JSONArray();

            List<Acceptor> acceptors = connectorService.getAcceptors();
            for (Acceptor acceptor : acceptors) {
                JSONObject jsonAcceptor = new JSONObject().put("name", Helper.getComponentName(acceptor));
                if (ExposableConfiguration.class.isInstance(acceptor)) {
                    jsonAcceptor.put("configuration", getConfigJSON((ExposableConfiguration<?>) acceptor));
                }
                jsonAcceptors.put(jsonAcceptor);
            }

            jsonConnector.put("acceptors", jsonAcceptors);
        }

        // include post-receiving-actions
        JSONArray jsonActions = getActionsJSON(connectorService.getPostReceivingActions());
        jsonConnector.put("post-receiving-actions", jsonActions);

        // include pre-processing-actions
        if (Processor.class.isInstance(connector)) {
            jsonActions = getActionsJSON(connectorService.getPreProcessingActions());
            jsonConnector.put("pre-processing-actions", jsonActions);

            jsonActions = getActionsJSON(connectorService.getPostProcessingActions());
            jsonConnector.put("post-processing-actions", jsonActions);
        }

        return jsonConnector;
    }

    /**
     * Helper method. Generates the JSON representation of a list of {@link Action} objects.
     *
     * @param actions a list of actions from which we are generating the JSON representation.
     *
     * @return a JSONArray that holds the information of the actions.
     * @throws JSONException
     */
    public static JSONArray getActionsJSON(List<Action> actions) throws JSONException {
        JSONArray jsonActions = new JSONArray();

        for (Action action : actions) {
            JSONObject jsonAction = new JSONObject().put("name", Helper.getComponentName(action));
            if (ExposableConfiguration.class.isInstance(action)) {
                jsonAction.put("configuration", getConfigJSON((ExposableConfiguration<?>) action));
            }
            jsonActions.put(jsonAction);
        }

        return jsonActions;
    }

    /**
     * Helper method. Generates the JSON representation of the configuration of connector/acceptor/action.
     *
     * @param o an object that implements {@link ExposableConfiguration} and from which we are generating the JSON
     * representation.
     *
     * @return a JSONObject that holds the information of the configuration.
     * @throws JSONException
     */
    public static <T extends ExposableConfiguration<?>> JSONObject getConfigJSON(T o) throws JSONException {
        Object config = o.getConfiguration();
        List<Field> fields = Helper.getConfigurationFields(config.getClass());

        JSONObject jsonConfig = new JSONObject();
        for (Field field : fields) {
            try {
                field.setAccessible(true);

                if (field.isAnnotationPresent(AcceptorsList.class)) {
                    Collection<Acceptor> acceptors = (Collection<Acceptor>) field.get(config);

                    JSONArray jsonAcceptors = new JSONArray();
                    for (Acceptor acceptor : acceptors) {
                        JSONObject jsonAcceptor = new JSONObject().put("name", Helper.getComponentName(acceptor));
                        if (ExposableConfiguration.class.isInstance(acceptor)) {
                            jsonAcceptor.put("configuration", getConfigJSON((ExposableConfiguration<?>) acceptor));
                        }
                        jsonAcceptors.put(jsonAcceptor);
                    }

                    jsonConfig.put(field.getName(), jsonAcceptors);
                } else {
                    jsonConfig.put(field.getName(), field.get(config));
                }
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }

        return jsonConfig;
    }

}