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

public val Icons.Rounded.Sort: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Sort",
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
				moveTo(120f, 720f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(240f)
				verticalLineToRelative(80f)
				close()
				moveToRelative(0f, -200f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(480f)
				verticalLineToRelative(80f)
				close()
				moveToRelative(0f, -200f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(720f)
				verticalLineToRelative(80f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
