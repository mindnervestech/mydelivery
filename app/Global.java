

import java.util.List;
import java.util.concurrent.TimeUnit;

import models.DeviceId;
import models.News;

import akka.actor.ActorSystem;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

public class Global extends GlobalSettings {
	public static final int CHAR_LEN=200;
	public static final String  APP_ENV_LOCAL = "local";
	public static final String  APP_ENV_VAR = "CURRENT_APPNAME";
	@Override
	public void onStart(Application app) {
		ActorSystem getLiveGame = Akka.system();
		getLiveGame.scheduler().schedule(
				Duration.create(1000, TimeUnit.MILLISECONDS),
				Duration.create(6, TimeUnit.HOURS), new Runnable() {
					public void run() {
						try {
								List<News> newsList = News.getAllNewNews();
								List<DeviceId> deviceList = DeviceId.getAll();
								for(News news: newsList) {
									for(DeviceId device: deviceList) {
										controllers.Application.sendPushNotification(device.id,news);
									}
									news.setNew(true);
									news.update();
								}
								
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, getLiveGame.dispatcher());
		
	}
	
	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}
	
}
