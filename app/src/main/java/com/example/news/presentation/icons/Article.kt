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

val Icons.Outlined.Article: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Article",
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
				moveTo(280f, 680f)
				horizontalLineToRelative(280f)
				verticalLineToRelative(-80f)
				horizontalLineTo(280f)
				close()
				moveToRelative(0f, -160f)
				horizontalLineToRelative(400f)
				verticalLineToRelative(-80f)
				horizontalLineTo(280f)
				close()
				moveToRelative(0f, -160f)
				horizontalLineToRelative(400f)
				verticalLineToRelative(-80f)
				horizontalLineTo(280f)
				close()
				moveToRelative(-80f, 480f)
				quadToRelative(-33f, 0f, -56.5f, -23.5f)
				reflectiveQuadTo(120f, 760f)
				verticalLineToRelative(-560f)
				quadToRelative(0f, -33f, 23.5f, -56.5f)
				reflectiveQuadTo(200f, 120f)
				horizontalLineToRelative(560f)
				quadToRelative(33f, 0f, 56.5f, 23.5f)
				reflectiveQuadTo(840f, 200f)
				verticalLineToRelative(560f)
				quadToRelative(0f, 33f, -23.5f, 56.5f)
				reflectiveQuadTo(760f, 840f)
				close()
				moveToRelative(0f, -80f)
				horizontalLineToRelative(560f)
				verticalLineToRelative(-560f)
				horizontalLineTo(200f)
				close()
				moveToRelative(0f, -560f)
				verticalLineToRelative(560f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
