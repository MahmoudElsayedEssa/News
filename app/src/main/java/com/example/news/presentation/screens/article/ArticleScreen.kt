package com.example.news.presentation.screens.article

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.news.R
import com.example.news.presentation.icons.Apps
import com.example.news.presentation.icons.Article
import com.example.news.presentation.icons.BookmarkBorder
import com.example.news.presentation.icons.Business
import com.example.news.presentation.icons.Category
import com.example.news.presentation.icons.CloudOff
import com.example.news.presentation.icons.Computer
import com.example.news.presentation.icons.Error
import com.example.news.presentation.icons.FilterList
import com.example.news.presentation.icons.HealthAndSafety
import com.example.news.presentation.icons.History
import com.example.news.presentation.icons.Movie
import com.example.news.presentation.icons.Public
import com.example.news.presentation.icons.Schedule
import com.example.news.presentation.icons.Science
import com.example.news.presentation.icons.Sort
import com.example.news.presentation.icons.SportsBasketball
import com.example.news.presentation.icons.TrendingUp
import com.example.news.presentation.icons.Tune
import com.example.news.presentation.screens.components.EmptyState
import com.example.news.presentation.utils.formatRelativeTime
import com.example.news.domain.model.Article
import com.example.news.domain.model.enums.Country
import com.example.news.domain.model.enums.NewsCategory
import com.example.news.domain.model.enums.SortBy
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf

/**
 * ArticleList Screen UI
 */
// ==================== ELEGANT ARTICLE LIST SCREEN ====================

/**
 * Elegant ArticleList Screen UI with creative design and latest Material 3
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListScreen(
    state: ArticleListState,
    actions: ArticleListActions,
    articles: LazyPagingItems<Article>
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isScrolled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                searchQuery = state.searchQuery,
                isSearchActive = state.isSearchActive,
                isScrolled = isScrolled,
                onSearchQueryChange = actions.onSearchQueryChange,
                onSearchSubmit = actions.onSearchSubmit,
                onSearchActiveChange = actions.onSearchActiveChange,
                onShowFilters = { actions.onShowFilters(true) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = actions.onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pullToRefreshState,
            indicator = {
                CustomPullToRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = state.isRefreshing,
                    state = pullToRefreshState
                )
            }
        ) {
            when {
                articles.loadState.refresh is LoadState.Loading && articles.itemCount == 0 -> {
                    LoadingState(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                articles.loadState.refresh is LoadState.Error && articles.itemCount == 0 -> {
                    ErrorState(
                        error = (articles.loadState.refresh as LoadState.Error).error.message
                            ?: "Something went wrong",
                        onRetry = actions.onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                articles.itemCount == 0 -> {
                    EmptyState(
//                        isSearchActive = state.isSearchActive,
//                        searchQuery = state.searchQuery,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                else -> {
                    ArticleList(
                        articles = articles,
                        onArticleClick = actions.onArticleClick,
                        onScrolled = { isScrolled = it },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    // Elegant Filters Sheet
    if (state.showFilters) {
        FiltersBottomSheet(
            selectedCategory = state.selectedCategory,
            selectedCountry = state.selectedCountry,
            selectedSortBy = state.selectedSortBy,
            onCategorySelected = actions.onCategorySelected,
            onCountrySelected = actions.onCountrySelected,
            onSortBySelected = actions.onSortBySelected,
            onDismiss = { actions.onShowFilters(false) }
        )
    }

    // Elegant Error Handling
    state.error?.let { error ->
        ErrorSnackbar(
            error = error,
            onDismiss = actions.onClearError
        )
    }
}



// ==================== CREATIVE TOP BAR ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    searchQuery: String,
    isSearchActive: Boolean,
    isScrolled: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    onShowFilters: () -> Unit
) {
    val animatedElevation by animateDpAsState(
        targetValue = if (isScrolled) 8.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "elevation"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isScrolled) 0.95f else 1f,
        animationSpec = tween(300),
        label = "alpha"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = animatedElevation,
        color = MaterialTheme.colorScheme.surface.copy(alpha = animatedAlpha)
    ) {
        Column(
            modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
        ) {
            // Hero Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Discover",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Latest news from around the world",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                FilledIconButton(
                    onClick = onShowFilters,
                    modifier = Modifier.size(48.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = "Filters",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Elegant Search Bar
            ElegantSearchBar(
                query = searchQuery,
                isActive = isSearchActive,
                onQueryChange = onSearchQueryChange,
                onSearch = onSearchSubmit,
                onActiveChange = onSearchActiveChange,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ElegantSearchBar(
    query: String,
    isActive: Boolean,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val animatedShape by animateIntAsState(
        targetValue = if (isActive) 16 else 32,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "shape"
    )

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = { onSearch() },
                expanded = isActive,
                onExpandedChange = onActiveChange,
                placeholder = {
                    Text(
                        "Search articles, sources, topics...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    AnimatedSearchIcon(isActive = isActive)
                },
                trailingIcon = {
                    AnimatedContent(
                        targetState = query.isNotEmpty(),
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) togetherWith
                                    fadeOut(animationSpec = tween(300))
                        },
                        label = "trailing_icon"
                    ) { hasQuery ->
                        if (hasQuery) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(
                                    Icons.Outlined.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                },
                colors = SearchBarDefaults.inputFieldColors(
//                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
//                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        expanded = isActive,
        onExpandedChange = onActiveChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(animatedShape.dp),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        shadowElevation = if (isActive) 8.dp else 2.dp,
        tonalElevation = 0.dp
    ) {
        SearchSuggestions(
            query = query,
            onSuggestionClick = { suggestion ->
                onQueryChange(suggestion)
                onSearch()
            }
        )
    }
}

@Composable
private fun AnimatedSearchIcon(isActive: Boolean) {
    val rotation by animateFloatAsState(
        targetValue = if (isActive) 90f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "search_rotation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isActive) 1.1f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "search_scale"
    )

    Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = "Search",
        tint = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .graphicsLayer {
                rotationZ = rotation
                scaleX = scale
                scaleY = scale
            }
    )
}

// ==================== CREATIVE ARTICLE LIST ====================

@Composable
private fun ArticleList(
    articles: LazyPagingItems<Article>,
    onArticleClick: (Article) -> Unit,
    onScrolled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset > 0 }
            .distinctUntilChanged()
            .collect { isScrolled ->
                onScrolled(isScrolled)
            }
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            count = articles.itemCount,
            key = articles.itemKey { it.id.value }
        ) { index ->
            val article = articles[index]
            if (article != null) {
                ArticleCard(
                    article = article,
                    onClick = { onArticleClick(article) },
                    modifier = Modifier
                        .animateItem(
                            fadeInSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            fadeOutSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                )
            }
        }

        // Loading States
        when (articles.loadState.append) {
            is LoadState.Loading -> {
                item {
                    LoadingMoreIndicator()
                }
            }
            is LoadState.Error -> {
                item {
//                    LoadMoreErrorCard(
//                        error = (articles.loadState.append as LoadState.Error).error.message
//                            ?: "Failed to load more articles",
//                        onRetry = { articles.retry() }
//                    )
                }
            }
            else -> {}
        }
    }
}

// ==================== CREATIVE ARTICLE CARD ====================

@Composable
private fun ArticleCard(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val animatedElevation by animateDpAsState(
        targetValue = if (isPressed) 1.dp else 6.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "elevation"
    )

    val animatedScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = animatedScale
                scaleY = animatedScale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = animatedElevation
        )
    ) {
        Column {
            // Hero Image Section
            article.imageUrl?.let { imageUrl ->
                HeroImageSection(
                    imageUrl = imageUrl.value,
                    sourceName = article.source.name.value
                )
            }

            // Content Section
            ContentSection(
                article = article,
                hasImage = article.imageUrl != null
            )
        }
    }
}

@Composable
private fun HeroImageSection(
    imageUrl: String,
    sourceName: String
) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .transformations(
                    RoundedCornersTransformation(topLeft = 20f, topRight = 20f)
                )
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
            error = painterResource(R.drawable.ic_launcher_background)
        )

        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 100f
                    )
                )
        )

        // Source Badge
        Surface(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
            shadowElevation = 4.dp
        ) {
            Text(
                text = sourceName,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Reading Time Badge
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.Black.copy(alpha = 0.6f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "3 min read",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ContentSection(
    article: Article,
    hasImage: Boolean
) {
    Column(
        modifier = Modifier.padding(
            top = if (hasImage) 20.dp else 24.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = 24.dp
        )
    ) {
        // Category Chip (if no image)
        if (!hasImage) {
            CategoryChip(sourceName = article.source.name.value)
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Title
        Text(
            text = article.title.value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Description
        article.description?.let { description ->
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description.value,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bottom Section
        BottomSection(article = article)
    }
}

@Composable
private fun CategoryChip(sourceName: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
    ) {
        Text(
            text = sourceName,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun BottomSection(article: Article) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time and Author
        Column {
            Text(
                text = formatRelativeTime(article.publishedAt.value),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )

            article.author?.let { author ->
                Text(
                    text = "by ${author.value}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Action Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ActionButton(
                icon = Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark"
            ) {
                // Handle bookmark
            }

            ActionButton(
                icon = Icons.Outlined.Share,
                contentDescription = "Share"
            ) {
                // Handle share
            }
        }
    }
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp)
        )
    }
}

// ==================== CREATIVE LOADING STATES ====================

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(56.dp),
                strokeWidth = 4.dp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Discovering amazing stories",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Please wait while we fetch the latest news",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun LoadingMoreIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 3.dp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Loading more stories...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ==================== CREATIVE ERROR STATES ====================

@Composable
private fun ErrorState(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated Error Icon
        var isAnimating by remember { mutableStateOf(true) }
        val infiniteTransition = rememberInfiniteTransition(label = "error_animation")
        val animatedScale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "error_scale"
        )

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer {
                    scaleX = if (isAnimating) animatedScale else 1f
                    scaleY = if (isAnimating) animatedScale else 1f
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.CloudOff,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(30.dp),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Oops! Something went wrong",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                isAnimating = false
                onRetry()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Try Again",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

// ==================== CREATIVE CUSTOM COMPONENTS ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomPullToRefreshIndicator(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    state: PullToRefreshState
) {
    val scale by animateFloatAsState(
        targetValue = if (state.distanceFraction > 0f || isRefreshing) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "indicator_scale"
    )

    Surface(
        modifier = modifier
            .size(56.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationY = state.distanceFraction * 80.dp.toPx() - 56.dp.toPx()
            },
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            if (isRefreshing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 3.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(24.dp)
                        .graphicsLayer {
                            rotationZ = state.distanceFraction * 180f
                        }
                )
            }
        }
    }
}

@Composable
private fun SearchSuggestions(
    query: String,
    onSuggestionClick: (String) -> Unit
) {
    if (query.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.heightIn(max = 300.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(5) { index ->
                ListItem(
                    headlineContent = {
                        Text(
                            "Search suggestion ${index + 1}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Outlined.History,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    modifier = Modifier
                        .clickable {
                            onSuggestionClick("Search suggestion ${index + 1}")
                        }
                        .animateItem()
                )
            }
        }
    }
}

// ==================== ELEGANT FILTERS BOTTOM SHEET ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBottomSheet(
    selectedCategory: NewsCategory?,
    selectedCountry: Country?,
    selectedSortBy: SortBy,
    onCategorySelected: (NewsCategory?) -> Unit,
    onCountrySelected: (Country?) -> Unit,
    onSortBySelected: (SortBy) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var isClosing by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            isClosing = true
            onDismiss()
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp,
//        shadowElevation = 24.dp,
        dragHandle = {
            ElegantDragHandle()
        },
    ) {
        FilterContent(
            selectedCategory = selectedCategory,
            selectedCountry = selectedCountry,
            selectedSortBy = selectedSortBy,
            onCategorySelected = onCategorySelected,
            onCountrySelected = onCountrySelected,
            onSortBySelected = onSortBySelected,
            onApply = {
                isClosing = true
                onDismiss()
            },
            isClosing = isClosing
        )
    }
}

@Composable
private fun ElegantDragHandle() {
    val infiniteTransition = rememberInfiniteTransition(label = "drag_handle")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drag_handle_alpha"
    )

    Surface(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .size(width = 40.dp, height = 5.dp),
        shape = RoundedCornerShape(2.5.dp),
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)
    ) {}
}

@Composable
private fun FilterContent(
    selectedCategory: NewsCategory?,
    selectedCountry: Country?,
    selectedSortBy: SortBy,
    onCategorySelected: (NewsCategory?) -> Unit,
    onCountrySelected: (Country?) -> Unit,
    onSortBySelected: (SortBy) -> Unit,
    onApply: () -> Unit,
    isClosing: Boolean
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isClosing) 0f else 1f,
        animationSpec = tween(300),
        label = "content_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .graphicsLayer { alpha = animatedAlpha }
    ) {
        // Header Section
        FilterHeader()

        Spacer(modifier = Modifier.height(32.dp))

        // Category Filter Section
        FilterSection(
            title = "Category",
            subtitle = "Choose your preferred news category",
            icon = Icons.Outlined.Category
        ) {
            CategoryFilterChips(
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Sort Filter Section
        FilterSection(
            title = "Sort by",
            subtitle = "Order articles by your preference",
            icon = Icons.Rounded.Sort
        ) {
            SortFilterChips(
                selectedSortBy = selectedSortBy,
                onSortBySelected = onSortBySelected
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Country Filter Section (Optional)
        FilterSection(
            title = "Region",
            subtitle = "Filter by specific countries",
            icon = Icons.Filled.Public
        ) {
            CountryFilterChips(
                selectedCountry = selectedCountry,
                onCountrySelected = onCountrySelected
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Action Buttons
        FilterActions(onApply = onApply)
    }
}

@Composable
private fun FilterHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Customize your news experience",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
        ) {
            Icon(
                imageVector = Icons.Rounded.FilterList,
                contentDescription = null,
                modifier = Modifier.padding(12.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun FilterSection(
    title: String,
    subtitle: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        content()
    }
}

@Composable
private fun CategoryFilterChips(
    selectedCategory: NewsCategory?,
    onCategorySelected: (NewsCategory?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            ElegantFilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = "All Categories",
                icon = Icons.Rounded.Apps
            )
        }

        items(NewsCategory.entries.toTypedArray()) { category ->
            ElegantFilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = category.displayName,
                icon = getCategoryIcon(category)
            )
        }
    }
}

@Composable
private fun SortFilterChips(
    selectedSortBy: SortBy,
    onSortBySelected: (SortBy) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(SortBy.entries.toTypedArray()) { sortOption ->
            ElegantFilterChip(
                selected = selectedSortBy == sortOption,
                onClick = { onSortBySelected(sortOption) },
                label = sortOption.displayName,
                icon = getSortIcon(sortOption)
            )
        }
    }
}

@Composable
private fun CountryFilterChips(
    selectedCountry: Country?,
    onCountrySelected: (Country?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            ElegantFilterChip(
                selected = selectedCountry == null,
                onClick = { onCountrySelected(null) },
                label = "All Regions",
                icon = Icons.Filled.Public
            )
        }

        items(Country.entries.toTypedArray().take(5)) { country -> // Show only first 5 for space
            ElegantFilterChip(
                selected = selectedCountry == country,
                onClick = { onCountrySelected(country) },
                label = country.displayName,
                icon = Icons.Rounded.LocationOn
            )
        }
    }
}

@Composable
private fun ElegantFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "chip_scale"
    )

    val animatedElevation by animateDpAsState(
        targetValue = if (selected) 8.dp else 2.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "chip_elevation"
    )

    Surface(
        onClick = onClick,
        modifier = modifier.graphicsLayer {
            scaleX = animatedScale
            scaleY = animatedScale
        },
        shape = RoundedCornerShape(16.dp),
        color = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainer
        },
        shadowElevation = animatedElevation,
        border = if (selected) {
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        } else null
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (selected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                color = if (selected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Composable
private fun FilterActions(onApply: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = { /* Reset filters */ },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Refresh,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Reset",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }

        Button(
            onClick = onApply,
            modifier = Modifier.weight(2f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Apply Filters",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}

// ==================== ELEGANT ERROR SNACKBAR ====================

@Composable
fun ErrorSnackbar(
    error: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        isVisible = true
        delay(4000) // Auto dismiss after 4 seconds
        isVisible = false
        delay(300) // Wait for animation to complete
        onDismiss()
    }

    AnimatedVisibility (
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ) + fadeOut(),
        modifier = modifier
    ) {
        ElegantErrorSnackbarContent(
            error = error,
            onDismiss = {
                isVisible = false
                onDismiss()
            }
        )
    }
}

@Composable
private fun ElegantErrorSnackbarContent(
    error: String,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.errorContainer,
        shadowElevation = 12.dp,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Animated Error Icon
            var isAnimating by remember { mutableStateOf(true) }
            val infiniteTransition = rememberInfiniteTransition(label = "error_icon")
            val animatedRotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = if (isAnimating) 10f else 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "error_rotation"
            )

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                modifier = Modifier.graphicsLayer {
                    rotationZ = animatedRotation
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Error,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = MaterialTheme.colorScheme.onError
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Error Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Something went wrong",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )

                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Dismiss Button
            FilledIconButton(
                onClick = {
                    isAnimating = false
                    onDismiss()
                },
                modifier = Modifier.size(36.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Dismiss",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ==================== UTILITY FUNCTIONS ====================

@Composable
private fun getCategoryIcon(category: NewsCategory): ImageVector {
    return when (category) {
        NewsCategory.BUSINESS -> Icons.Rounded.Business
        NewsCategory.ENTERTAINMENT -> Icons.Rounded.Movie
        NewsCategory.GENERAL -> Icons.Outlined.Article
        NewsCategory.HEALTH -> Icons.Rounded.HealthAndSafety
        NewsCategory.SCIENCE -> Icons.Rounded.Science
        NewsCategory.SPORTS -> Icons.Rounded.SportsBasketball
        NewsCategory.TECHNOLOGY -> Icons.Rounded.Computer
    }
}

@Composable
private fun getSortIcon(sortBy: SortBy): ImageVector {
    return when (sortBy) {
        SortBy.RELEVANCY -> Icons.Rounded.TrendingUp
        SortBy.POPULARITY -> Icons.Rounded.Star
        SortBy.PUBLISHED_AT -> Icons.Outlined.Schedule
    }
}

// ==================== USAGE IN MAIN COMPOSABLE ====================

@Composable
fun ErrorSnackbarHost(
    error: String?,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        error?.let { errorMessage ->
            ErrorSnackbar(
                error = errorMessage,
                onDismiss = onDismiss
            )
        }
    }
}

// ==================== UTILITY FUNCTIONS ====================

fun formatRelativeTime(publishedAt: String): String {
    // Sophisticated time formatting
    return "2 hours ago" // Placeholder - implement actual logic
}

// ==================== PREVIEWS ====================

@Preview(name = "Elegant ArticleList", showBackground = true)
@Composable
private fun ArticleListScreenPreview() {
    MaterialTheme {
        ArticleListScreen(
            state = ArticleListState(),
            actions = ArticleListActions(),
            articles = flowOf(PagingData.empty<Article>()).collectAsLazyPagingItems()
        )
    }
}