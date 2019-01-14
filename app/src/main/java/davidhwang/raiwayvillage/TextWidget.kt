package davidhwang.raiwayvillage

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [TextWidgetConfigureActivity]
 */
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

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val list1 = listOf<String>(
                    "面對陽光，黑暗就會在背後",
                    "我不怕千萬人阻擋，只怕自己投降",
                    "人之所以能，是相信能",
                    "不要等待機會而要創造機會",
                    "自信的生命最美麗",
                    "含淚播種的人一定能含笑收穫")

            val widgetText = list1[0]
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.text_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

