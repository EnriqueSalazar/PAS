package co.gov.fonada.planeacion.config;


import java.util.Map;
//import java.lang.reflect.Field;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class SessionSerializationPhaseListener implements PhaseListener {

	// private static Logger log =
	// Logger.getLogger(SessionSerializationPhaseListener.class);

	public SessionSerializationPhaseListener() {
	}

	private void serialSession() {
		// log.debug("Serializando una session web.");
		System.out.println("SessionSerializationPhaseListener serialSession");
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final Map<String, Object> sessionMap = facesContext
				.getExternalContext().getSessionMap();
//		System.out.println("SessionSerializationPhaseListener sessionMap ["+sessionMap.entrySet().size()+"] "+sessionMap.entrySet().toString());
//		try{
//			for (Map.Entry<String, Object> entry : sessionMap.entrySet()) {
//				String key = entry.getKey();
//				String value=entry.getValue().toString();
//				System.out.println("==========================================================");
//				System.out.println("KEY " + key);
//				System.out.println(" ");
//				System.out.println("VALUE");
//				System.out.println(value);
//				System.out.println("==========================================================");
//			}
//		}
//		catch(Exception ex){
//			ex.printStackTrace();
//		}
		sessionMap.put("forceGaeSessionSerialization",
				System.currentTimeMillis());
		System.out
				.println("SessionSerializationPhaseListener forceGaeSessionSerialization");

	}

//	public static String dump(Object object) {
//		Field[] fields = object.getClass().getDeclaredFields();
//		StringBuilder sb = new StringBuilder();
//		sb.append(object.getClass().getSimpleName()).append('{');
//
//		boolean firstRound = true;
//
//		for (Field field : fields) {
//			if (!firstRound) {
//				sb.append(", ");
//			}
//			firstRound = false;
//			field.setAccessible(true);
//			try {
//				final Object fieldObj = field.get(object);
//				final String value;
//				if (null == fieldObj) {
//					value = "null";
//				} else {
//					value = fieldObj.toString();
//				}
//				sb.append(field.getName()).append('=').append('\'')
//						.append(value).append('\'');
//			} catch (IllegalAccessException ignore) {
//				// this should never happen
//			}
//
//		}
//
//		sb.append('}');
//		return sb.toString();
//	}

	public void afterPhase(final PhaseEvent event) {
		if (event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)
				|| event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)
				|| (event.getPhaseId().equals(PhaseId.APPLY_REQUEST_VALUES) && event
						.getFacesContext().getResponseComplete())) {
			System.out.println("AFTERPHASE");
			serialSession();
			System.out.println("SERIALSESSION");

		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent event) {
	}

}
