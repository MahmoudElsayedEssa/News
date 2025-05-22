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

public val Icons.Rounded.HealthAndSafety: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Health_and_safety",
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
				moveTo(420f, 620f)
				horizontalLineToRelative(120f)
				verticalLineToRelative(-100f)
				horizontalLineToRelative(100f)
				verticalLineToRelative(-120f)
				horizontalLineTo(540f)
				verticalLineToRelative(-100f)
				horizontalLineTo(420f)
				verticalLineToRelative(100f)
				horizontalLineTo(320f)
				verticalLineToRelative(120f)
				horizontalLineToRelative(100f)
				close()
				moveToRelative(60f, 260f)
				quadToRelative(-139f, -35f, -229.5f, -159.5f)
				reflectiveQuadTo(160f, 444f)
				verticalLineToRelative(-244f)
				lineToRelative(320f, -120f)
				lineToRelative(320f, 120f)
				verticalLineToRelative(244f)
				quadToRelative(0f, 152f, -90.5f, 276.5f)
				reflectiveQuadTo(480f, 880f)
				moveToRelative(0f, -84f)
				quadToRelative(104f, -33f, 172f, -132f)
				reflectiveQuadToRelative(68f, -220f)
				verticalLineToRelative(-189f)
				lineToRelative(-240f, -90f)
				lineToRelative(-240f, 90f)
				verticalLineToRelative(189f)
				quadToRelative(0f, 121f, 68f, 220f)
				reflectiveQuadToRelative(172f, 132f)
				moveToRelative(0f, -316f)
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
