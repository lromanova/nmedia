package ru.netology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.observe
import ru.netology.databinding.ActivityMainBinding
import ru.netology.dbo.Post
import ru.netology.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    var countFavorite: Int = 999
    var countShared: Int = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(
            this
        ) { post ->
            with(binding) {
                txtFavorite.text = convertCountToString(countFavorite)
                txtShare.text = convertCountToString(countShared)
                txtTitle.text = post.author
                txtAbout.text = post.content
                txtCurrentDate.text = post.published
                if (post.likedByMe) imgbtnFavorite.setImageResource(R.drawable.ic_baseline_favorite_red_24)

                imgbtnFavorite.setOnClickListener {
                    countFavorite = if (post.likedByMe) countFavorite - 1 else countFavorite + 1
                    txtFavorite.text = convertCountToString(countFavorite)
                    //post.likedByMe = !post.likedByMe
                    viewModel.like()
                    binding.imgbtnFavorite.setImageResource(
                        if (post.likedByMe) R.drawable.ic_baseline_favorite_red_24 else R.drawable.ic_baseline_favorite_24
                    )
                }

                binding.imgbtnShare.setOnClickListener{
                    countShared += 1
                    txtShare.text = convertCountToString(countShared)
                }
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
        val res = if (count % 1000 == 0) {
            (count/1000).toString()
        } else {
            val toRound = (count.toDouble()/1000).toString()
            if (toRound.substring(2,3) == "0"){
                (count/1000).toString()
            } else {
                toRound.substring(0, 3)
            }
        }

        return "$res K"
    }


    private fun getHundredThousands(count: Int): String {

        return (count/1000).toString() + " K"
    }

    private fun getMillions(count: Int): String {

        val res = if (count % 1_000_000 == 0) {
            (count/1_000_000).toString()
        } else {
            if (count.toString().substring(count.toString().length-6, count.toString().length -5) == "0"){
                (count/1_000_000).toString()
            } else {
               count.toString().substring(0, count.toString().length-6) + "." + count.toString().substring(count.toString().length-6, count.toString().length-5)
            }
        }

        return "$res M"
    }
}


