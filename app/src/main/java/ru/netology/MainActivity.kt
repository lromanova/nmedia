package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.netology.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    var countFavorite: Int = 999
    var countShared: Int = 1_534_567

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initForm(createPost(),binding)

    }

   private fun createPost(): Post {
       val author = R.string.head_tytle
       return  Post (
            id = 1,
            author = this.getString(R.string.head_tytle),
            content = this.getString(R.string.text_about),
            published = this.getString(R.string.head_time),
            likedByMe = false
        )
    }

   private fun initForm(post: Post, binding: ActivityMainBinding){
        with(binding) {
            txtFavorite.text = convertCountToString(countFavorite)
            txtShare.text = convertCountToString(countShared)
            txtTitle.text = post.author
            txtAbout.text = post.content
            txtCurrentDate.text = post.published
            if(post.likedByMe) imgbtnFavorite.setImageResource(R.drawable.ic_baseline_favorite_red_24)

            imgbtnFavorite?.setOnClickListener {
                countFavorite = if(post.likedByMe) countFavorite - 1 else countFavorite + 1
                //txtFavorite.text = countFavorite.toString()
                txtFavorite.text = convertCountToString(countFavorite)
                post.likedByMe = !post.likedByMe
                imgbtnFavorite.setImageResource(
                    if(post.likedByMe) R.drawable.ic_baseline_favorite_red_24 else R.drawable.ic_baseline_favorite_24
                )
            }

            imgbtnShare?.setOnClickListener{
                countShared += 1
                txtShare.text = convertCountToString(countShared)
            }
        }
    }

    private fun convertCountToString(count: Int): String {

        return when {
            (count < 1000) -> count.toString()
            ((count > 999) && (count < 10_000)) -> getThousands(count)
            ((count > 9_999) && (count < 1_000_000)) -> getHundredThousands(count)
            (count > 999_999) -> getMillions(count)
            else -> count.toString()
        }
    }

    private fun getThousands(count: Int): String {
        val builder: StringBuilder = StringBuilder()
        val arr: List<String> = count.toString().split("")

        builder.append(arr[1])
        if (arr[2] != "0") {
            builder.append(",")
            builder.append(arr[2])
        }
        builder.append(" K")

        return builder.toString()
    }

    private fun getHundredThousands(count: Int): String {

        return count.toString().substring(0, count.toString().length - 3) + " K"
    }

    private fun getMillions(count: Int): String {

        val builder: StringBuilder = StringBuilder()
        val countLength = count.toString().length

        builder.append(count.toString().substring(0,countLength - 6))
        if (count.toString().substring (countLength - 6,countLength - 5) != "0") {
            builder.append(",")
            builder.append(count.toString().substring(countLength - 6,countLength - 5))
        }
        builder.append(" M")

        return builder.toString()
    }
}


