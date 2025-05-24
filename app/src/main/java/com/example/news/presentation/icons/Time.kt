package com.example.news.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Time: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Avg_time",
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
				moveTo(203f, 480f)
				horizontalLineToRelative(117f)
				quadToRelative(11f, 0f, 21f, 5.5f)
				reflectiveQuadToRelative(15f, 16.5f)
				lineToRelative(44f, 88f)
				lineToRelative(124f, -248f)
				quadToRelative(11f, -23f, 36f, -23f)
				reflectiveQuadToRelative(36f, 23f)
				lineToRelative(69f, 138f)
				horizontalLineToRelative(92f)
				quadToRelative(-15f, -102f, -93f, -171f)
				reflectiveQuadToRelative(-184f, -69f)
				reflectiveQuadToRelative(-184f, 69f)
				reflectiveQuadToRelative(-93f, 171f)
				moveToRelative(277f, 320f)
				quadToRelative(106f, 0f, 184f, -69f)
				reflectiveQuadToRelative(93f, -171f)
				horizontalLineTo(640f)
				quadToRelative(-11f, 0f, -21f, -5.5f)
				reflectiveQuadTo(604f, 538f)
				lineToRelative(-44f, -88f)
				lineToRelative(-124f, 248f)
				quadToRelative(-11f, 23f, -36f, 23f)
				reflectiveQuadToRelative(-36f, -23f)
				lineToRelative(-69f, -138f)
				horizontalLineToRelative(-92f)
				quadToRelative(15f, 102f, 93f, 171f)
				reflectiveQuadToRelative(184f, 69f)
				moveToRelative(0f, 80f)
				quadToRelative(-74f, 0f, -139.5f, -28.5f)
				reflectiveQuadTo(226f, 774f)
				reflectiveQuadToRelative(-77.5f, -114.5f)
				reflectiveQuadTo(120f, 520f)
				horizontalLineToRelative(80f)
				quadToRelative(0f, 116f, 82f, 198f)
				reflectiveQuadToRelative(198f, 82f)
				reflectiveQuadToRelative(198f, -82f)
				reflectiveQuadToRelative(82f, -198f)
				horizontalLineToRelative(80f)
				quadToRelative(0f, 74f, -28.5f, 139.5f)
				reflectiveQuadTo(734f, 774f)
				reflectiveQuadToRelative(-114.5f, 77.5f)
				reflectiveQuadTo(480f, 880f)
				moveTo(120f, 520f)
				quadToRelative(0f, -74f, 28.5f, -139.5f)
				reflectiveQuadTo(226f, 266f)
				reflectiveQuadToRelative(114.5f, -77.5f)
				reflectiveQuadTo(480f, 160f)
				quadToRelative(62f, 0f, 119f, 20f)
				reflectiveQuadToRelative(107f, 58f)
				lineToRelative(56f, -56f)
				lineToRelative(56f, 56f)
				lineToRelative(-56f, 56f)
				quadToRelative(38f, 50f, 58f, 107f)
				reflectiveQuadToRelative(20f, 119f)
				horizontalLineToRelative(-80f)
				quadToRelative(0f, -116f, -82f, -198f)
				reflectiveQuadToRelative(-198f, -82f)
				reflectiveQuadToRelative(-198f, 82f)
				reflectiveQuadToRelative(-82f, 198f)
				close()
				moveToRelative(240f, -400f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(240f)
				verticalLineToRelative(80f)
				close()
				moveToRelative(120f, 680f)
				quadToRelative(-116f, 0f, -198f, -82f)
				reflectiveQuadToRelative(-82f, -198f)
				reflectiveQuadToRelative(82f, -198f)
				reflectiveQuadToRelative(198f, -82f)
				reflectiveQuadToRelative(198f, 82f)
				reflectiveQuadToRelative(82f, 198f)
				reflectiveQuadToRelative(-82f, 198f)
				reflectiveQuadToRelative(-198f, 82f)
				moveToRelative(0f, -280f)
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
