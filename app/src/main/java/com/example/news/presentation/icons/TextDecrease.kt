package com.example.news.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val TextDecrease: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
            name = "Text_decrease",
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
				moveTo(40f, 760f)
				lineToRelative(210f, -560f)
				horizontalLineToRelative(100f)
				lineToRelative(210f, 560f)
				horizontalLineToRelative(-96f)
				lineToRelative(-51f, -143f)
				horizontalLineTo(187f)
				lineToRelative(-51f, 143f)
				close()
				moveToRelative(176f, -224f)
				horizontalLineToRelative(168f)
				lineToRelative(-82f, -232f)
				horizontalLineToRelative(-4f)
				close()
				moveToRelative(384f, -16f)
				verticalLineToRelative(-80f)
				horizontalLineToRelative(320f)
				verticalLineToRelative(80f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
