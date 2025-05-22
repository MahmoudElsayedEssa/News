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

public val Icons.Rounded.Movie: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Movie",
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
				moveTo(160f, 160f)
				lineToRelative(80f, 160f)
				horizontalLineToRelative(120f)
				lineToRelative(-80f, -160f)
				horizontalLineToRelative(80f)
				lineToRelative(80f, 160f)
				horizontalLineToRelative(120f)
				lineToRelative(-80f, -160f)
				horizontalLineToRelative(80f)
				lineToRelative(80f, 160f)
				horizontalLineToRelative(120f)
				lineToRelative(-80f, -160f)
				horizontalLineToRelative(120f)
				quadToRelative(33f, 0f, 56.5f, 23.5f)
				reflectiveQuadTo(880f, 240f)
				verticalLineToRelative(480f)
				quadToRelative(0f, 33f, -23.5f, 56.5f)
				reflectiveQuadTo(800f, 800f)
				horizontalLineTo(160f)
				quadToRelative(-33f, 0f, -56.5f, -23.5f)
				reflectiveQuadTo(80f, 720f)
				verticalLineToRelative(-480f)
				quadToRelative(0f, -33f, 23.5f, -56.5f)
				reflectiveQuadTo(160f, 160f)
				moveToRelative(0f, 240f)
				verticalLineToRelative(320f)
				horizontalLineToRelative(640f)
				verticalLineToRelative(-320f)
				close()
				moveToRelative(0f, 0f)
				verticalLineToRelative(320f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
