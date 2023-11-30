package kr.ac.kumoh.ce.s20171225.w1301songlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.ac.kumoh.ce.s20171225.w1301songlist.ui.theme.W1301SongListTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage


private val songs = mutableListOf<Song>()

class MainActivity : ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: SongViewModel) {
    val songList by viewModel.songList.observeAsState(emptyList())
    W1301SongListTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MyList(songList)
        }
    }

}

@Composable
fun MyList(list: List<Song>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(list) { index, song ->
            SongItem(index, song)
        }
    }
}

@Composable
fun SongItem(index: Int, song: Song) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
//            .background(Color(255, 255, 255))
                .padding(8.dp)
        ) {
            AsyncImage(
                model = "https://picsum.photos/300/300?random=${index}",
                contentDescription = "노래 앨범 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
//                .clip(CircleShape)
                    .clip(RoundedCornerShape(10))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
//                .padding(16.dp)
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                TextTitle(title = "${song.title}")
                TextSinger(title = "${song.singer}")
            }
        }
        AnimatedVisibility(visible = expanded) {
//            TextLyrics(title = "${song.lyrics}")
            song.lyrics?.let { Text(it)} // nullable
        }
    }
}

@Composable
fun TextTitle(title: String) {
    Text(title, fontSize = 30.sp)
}

@Composable
fun TextSinger(title: String) {
    Text(title, fontSize = 20.sp)
}

@Composable
fun TextLyrics(title: String) {
    Text(title, fontSize = 15.sp)
}