package com.example.news.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val FormatSize: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Format_size",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
			path(
    			fill = SolidColor(Color.Black),
    			fillAlpha = 1.0f,
    			stroke = null,
    			strokeAlpha = 1.0f,
    			strokeLineWidth = 1.0f,
    			strokeLineCap = StrokeCap.Butt,
    			strokeLineJoin = StrokeJoin.Miter,
    			strokeLineMiter = 1.0f,
    			pathFillType = PathFillType.NonZero
			) {
				moveTo(560f, 800f)
				verticalLineToRelative(-520f)
				horizontalLineTo(360f)
				verticalLineToRelative(-120f)
				horizontalLineToRelative(520f)
				verticalLineToRelative(120f)
				horizontalLineTo(680f)
				verticalLineToRelative(520f)
				close()
				moveToRelative(-360f, 0f)
				verticalLineToRelative(-320f)
				horizontalLineTo(80f)
				verticalLineToRelative(-120f)
				horizontalLineToRelative(360f)
				verticalLineToRelative(120f)
				horizontalLineTo(320f)
				verticalLineToRelative(320f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
