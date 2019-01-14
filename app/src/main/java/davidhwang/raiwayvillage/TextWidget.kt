package davidhwang.raiwayvillage

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [TextWidgetConfigureActivity]
 */

const val WIDGET_SYNC = "WIDGET_SYNC"

class TextWidget : AppWidgetProvider() {


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            //TextWidgetConfigureActivity.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent?) {
        //Log.i("receive", "Test");
        if(WIDGET_SYNC == intent?.action) {
            val appWidgetId = intent.getIntExtra("appWidgetId",0)
            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId)
        }
        super.onReceive(context, intent)
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            Log.i("update", "Test");
            val intent = Intent(context,TextWidget::class.java)
            intent.action = WIDGET_SYNC
            intent.putExtra("appWidgetId",appWidgetId)
            val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.text_widget)
            val list1 = listOf<String>(
                    "面對陽光，黑暗就會在背後",
                    "我不怕他人阻擋，只怕自己投降",
                    "人之所以能，是相信能",
                    "不要等待機會而要創造機會",
                    "自信的生命最美麗",
                    "含淚播種的人一定能含笑收穫")
            var i : Int = (0..5).shuffled().first()
            views.setTextViewText(R.id.appwidget_text, list1[i])
            Log.i("list", "Test");
            views.setOnClickPendingIntent(R.id.iv_sync,pendingIntent)
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

