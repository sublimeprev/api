package com.sublimeprev.api.firebase;

import java.util.HashMap;
import java.util.List;

public class FirebaseNotification {
	
	public static String send(List<String> targets, String title, String message, String appID, String serverKey){
		// create raven object using your firebase messaging key
		Pushraven raven = new Pushraven(serverKey);
		// build raven message using the builder pattern
		raven.addAllMulticasts(targets)
			.collapse_key("a_collapse_key")
			.priority(10)
			.delay_while_idle(false)
			.time_to_live(100)
			.restricted_package_name(appID)
			.dry_run(false)
			.title(title)
			.body(message)
			.color("#3E4095").sound("default");
		//add utf-8
		// push the raven message
		FcmResponse r = raven.push();
		return r.getResponseMessage();
		
	}
	
	public static String sendChat(List<String> targets, String title, String message, String appID, String serverKey){
		// create raven object using your firebase messaging key
		Pushraven raven = new Pushraven(serverKey);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("view", "chat");
		// build raven message using the builder pattern
		raven.addAllMulticasts(targets)
			.collapse_key("a_collapse_key")
			.priority(10)
			.delay_while_idle(false)
			.time_to_live(100)
			.restricted_package_name(appID)
			.dry_run(false)
			.title(title)
			.body(message)
			.color("#3E4095").sound("default").data(data);
		// push the raven message
		FcmResponse r = raven.push();
		return r.getResponseMessage();		
	}
}
