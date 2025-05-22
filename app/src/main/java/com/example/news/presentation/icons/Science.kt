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

public val Icons.Rounded.Science: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Science",
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
				moveTo(200f, 840f)
				quadToRelative(-51f, 0f, -72.5f, -45.5f)
				reflectiveQuadTo(138f, 710f)
				lineToRelative(222f, -270f)
				verticalLineToRelative(-240f)
				horizontalLineToRelative(-40f)
				quadToRelative(-17f, 0f, -28.5f, -11.5f)
				reflectiveQuadTo(280f, 160f)
				reflectiveQuadToRelative(11.5f, -28.5f)
				reflectiveQuadTo(320f, 120f)
				horizontalLineToRelative(320f)
				quadToRelative(17f, 0f, 28.5f, 11.5f)
				reflectiveQuadTo(680f, 160f)
				reflectiveQuadToRelative(-11.5f, 28.5f)
				reflectiveQuadTo(640f, 200f)
				horizontalLineToRelative(-40f)
				verticalLineToRelative(240f)
				lineToRelative(222f, 270f)
				quadToRelative(32f, 39f, 10.5f, 84.5f)
				reflectiveQuadTo(760f, 840f)
				close()
				moveToRelative(0f, -80f)
				horizontalLineToRelative(560f)
				lineTo(520f, 468f)
				verticalLineToRelative(-268f)
				horizontalLineToRelative(-80f)
				verticalLineToRelative(268f)
				close()
				moveToRelative(280f, -280f)
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
