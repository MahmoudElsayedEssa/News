package com.example.news.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Storage: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Storage",
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
				moveTo(120f, 800f)
				verticalLineToRelative(-160f)
				horizontalLineToRelative(720f)
				verticalLineToRelative(160f)
				close()
				moveToRelative(80f, -40f)
				horizontalLineToRelative(80f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(-80f)
				close()
				moveToRelative(-80f, -440f)
				verticalLineToRelative(-160f)
				horizontalLineToRelative(720f)
				verticalLineToRelative(160f)
				close()
				moveToRelative(80f, -40f)
				horizontalLineToRelative(80f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(-80f)
				close()
				moveToRelative(-80f, 280f)
				verticalLineToRelative(-160f)
				horizontalLineToRelative(720f)
				verticalLineToRelative(160f)
				close()
				moveToRelative(80f, -40f)
				horizontalLineToRelative(80f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(-80f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
