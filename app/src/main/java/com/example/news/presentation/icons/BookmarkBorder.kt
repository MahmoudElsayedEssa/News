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

val BookmarkBorder: ImageVector
	get() {
		if (_undefined != null) {
			return _undefined!!
		}
		_undefined = ImageVector.Builder(
			name = "Bookmark",
			defaultWidth = 15.dp,
			defaultHeight = 15.dp,
			viewportWidth = 15f,
			viewportHeight = 15f
		).apply {
			path(
				fill = SolidColor(Color(0xFF000000)),
				fillAlpha = 1.0f,
				stroke = null,
				strokeAlpha = 1.0f,
				strokeLineWidth = 1.0f,
				strokeLineCap = StrokeCap.Butt,
				strokeLineJoin = StrokeJoin.Miter,
				strokeLineMiter = 1.0f,
				pathFillType = PathFillType.EvenOdd
			) {
				moveTo(3f, 2.5f)
				curveTo(3f, 2.2239f, 3.2239f, 2f, 3.5f, 2f)
				horizontalLineTo(11.5f)
				curveTo(11.7761f, 2f, 12f, 2.2239f, 12f, 2.5f)
				verticalLineTo(13.5f)
				curveTo(12f, 13.6818f, 11.9014f, 13.8492f, 11.7424f, 13.9373f)
				curveTo(11.5834f, 14.0254f, 11.3891f, 14.0203f, 11.235f, 13.924f)
				lineTo(7.5f, 11.5896f)
				lineTo(3.765f, 13.924f)
				curveTo(3.6109f, 14.0203f, 3.4166f, 14.0254f, 3.2576f, 13.9373f)
				curveTo(3.0986f, 13.8492f, 3f, 13.6818f, 3f, 13.5f)
				verticalLineTo(2.5f)
				close()
				moveTo(4f, 3f)
				verticalLineTo(12.5979f)
				lineTo(6.97f, 10.7416f)
				curveTo(7.2943f, 10.539f, 7.7057f, 10.539f, 8.03f, 10.7416f)
				lineTo(11f, 12.5979f)
				verticalLineTo(3f)
				horizontalLineTo(4f)
				close()
			}
		}.build()
		return _undefined!!
	}

private var _undefined: ImageVector? = null
