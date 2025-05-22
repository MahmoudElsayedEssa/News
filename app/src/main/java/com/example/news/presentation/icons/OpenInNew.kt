package com.example.news.presentation.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Outlined.OpenInNew: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "OpenInNewWindow",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
			path(
    			fill = SolidColor(Color(0xFF000000)),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.EvenOdd
			) {
				moveTo(12f, 13f)
				curveTo(12.5523f, 13f, 13f, 12.5523f, 13f, 12f)
				verticalLineTo(3f)
				curveTo(13f, 2.4477f, 12.5523f, 2f, 12f, 2f)
				horizontalLineTo(3f)
				curveTo(2.4477f, 2f, 2f, 2.4477f, 2f, 3f)
				verticalLineTo(6.5f)
				curveTo(2f, 6.7761f, 2.2239f, 7f, 2.5f, 7f)
				curveTo(2.7761f, 7f, 3f, 6.7761f, 3f, 6.5f)
				verticalLineTo(3f)
				horizontalLineTo(12f)
				verticalLineTo(12f)
				horizontalLineTo(8.5f)
				curveTo(8.2239f, 12f, 8f, 12.2239f, 8f, 12.5f)
				curveTo(8f, 12.7761f, 8.2239f, 13f, 8.5f, 13f)
				horizontalLineTo(12f)
				close()
				moveTo(9f, 6.5f)
				curveTo(9f, 6.5001f, 9f, 6.5002f, 9f, 6.5003f)
				verticalLineTo(6.50035f)
				verticalLineTo(9.5f)
				curveTo(9f, 9.7761f, 8.7761f, 10f, 8.5f, 10f)
				curveTo(8.2239f, 10f, 8f, 9.7761f, 8f, 9.5f)
				verticalLineTo(7.70711f)
				lineTo(2.85355f, 12.8536f)
				curveTo(2.6583f, 13.0488f, 2.3417f, 13.0488f, 2.1465f, 12.8536f)
				curveTo(1.9512f, 12.6583f, 1.9512f, 12.3417f, 2.1465f, 12.1464f)
				lineTo(7.29289f, 7f)
				horizontalLineTo(5.5f)
				curveTo(5.2239f, 7f, 5f, 6.7761f, 5f, 6.5f)
				curveTo(5f, 6.2239f, 5.2239f, 6f, 5.5f, 6f)
				horizontalLineTo(8.5f)
				curveTo(8.5678f, 6f, 8.6324f, 6.0135f, 8.6914f, 6.0379f)
				curveTo(8.7495f, 6.062f, 8.804f, 6.0974f, 8.8514f, 6.1443f)
				curveTo(8.9425f, 6.2343f, 8.9992f, 6.3591f, 9f, 6.4971f)
				lineTo(8.99999f, 6.49738f)
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
