package com.example.news.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val HourglassEmpty: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Hourglass_empty",
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
				moveTo(320f, 800f)
				horizontalLineToRelative(320f)
				verticalLineToRelative(-120f)
				quadToRelative(0f, -66f, -47f, -113f)
				reflectiveQuadToRelative(-113f, -47f)
				reflectiveQuadToRelative(-113f, 47f)
				reflectiveQuadToRelative(-47f, 113f)
				close()
				moveToRelative(160f, -360f)
				quadToRelative(66f, 0f, 113f, -47f)
				reflectiveQuadToRelative(47f, -113f)
				verticalLineToRelative(-120f)
				horizontalLineTo(320f)
				verticalLineToRelative(120f)
				quadToRelative(0f, 66f, 47f, 113f)
				reflectiveQuadToRelative(113f, 47f)
				moveTo(160f, 880f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(80f)
				verticalLineToRelative(-120f)
				quadToRelative(0f, -61f, 28.5f, -114.5f)
				reflectiveQuadTo(348f, 480f)
				quadToRelative(-51f, -32f, -79.5f, -85.5f)
				reflectiveQuadTo(240f, 280f)
				verticalLineToRelative(-120f)
				horizontalLineToRelative(-80f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(640f)
				verticalLineToRelative(80f)
				horizontalLineToRelative(-80f)
				verticalLineToRelative(120f)
				quadToRelative(0f, 61f, -28.5f, 114.5f)
				reflectiveQuadTo(612f, 480f)
				quadToRelative(51f, 32f, 79.5f, 85.5f)
				reflectiveQuadTo(720f, 680f)
				verticalLineToRelative(120f)
				horizontalLineToRelative(80f)
				verticalLineToRelative(80f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
