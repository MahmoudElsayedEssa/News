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

val CloudOff: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Cloud_off",
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
				moveTo(792f, 904f)
				lineTo(686f, 800f)
				horizontalLineTo(260f)
				quadToRelative(-92f, 0f, -156f, -64f)
				reflectiveQuadTo(40f, 580f)
				quadToRelative(0f, -77f, 47.5f, -137f)
				reflectiveQuadTo(210f, 366f)
				quadToRelative(3f, -8f, 6f, -15.5f)
				reflectiveQuadToRelative(6f, -16.5f)
				lineTo(56f, 168f)
				lineToRelative(56f, -56f)
				lineToRelative(736f, 736f)
				close()
				moveTo(260f, 720f)
				horizontalLineToRelative(346f)
				lineTo(284f, 398f)
				quadToRelative(-2f, 11f, -3f, 21f)
				reflectiveQuadToRelative(-1f, 21f)
				horizontalLineToRelative(-20f)
				quadToRelative(-58f, 0f, -99f, 41f)
				reflectiveQuadToRelative(-41f, 99f)
				reflectiveQuadToRelative(41f, 99f)
				reflectiveQuadToRelative(99f, 41f)
				moveToRelative(604f, 30f)
				lineToRelative(-58f, -56f)
				quadToRelative(17f, -14f, 25.5f, -32.5f)
				reflectiveQuadTo(840f, 620f)
				quadToRelative(0f, -42f, -29f, -71f)
				reflectiveQuadToRelative(-71f, -29f)
				horizontalLineToRelative(-60f)
				verticalLineToRelative(-80f)
				quadToRelative(0f, -83f, -58.5f, -141.5f)
				reflectiveQuadTo(480f, 240f)
				quadToRelative(-27f, 0f, -52f, 6.5f)
				reflectiveQuadTo(380f, 267f)
				lineToRelative(-58f, -58f)
				quadToRelative(35f, -24f, 74.5f, -36.5f)
				reflectiveQuadTo(480f, 160f)
				quadToRelative(117f, 0f, 198.5f, 81.5f)
				reflectiveQuadTo(760f, 440f)
				quadToRelative(69f, 8f, 114.5f, 59.5f)
				reflectiveQuadTo(920f, 620f)
				quadToRelative(0f, 39f, -15f, 72.5f)
				reflectiveQuadTo(864f, 750f)
				moveTo(593f, 481f)
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
