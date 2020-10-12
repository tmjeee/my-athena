package com.tmjee.myathena.ui.payeeAndLinkedAccounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.squareup.picasso.Picasso
import com.tmjee.myathena.R
import com.tmjee.myathena.databinding.RecyclerViewPhotosBinding
import com.tmjee.myathena.databinding.RecyclerViewPhotosStateBinding
import com.tmjee.myathena.databinding.ViewPagerFragmentPhotosBinding
import com.tmjee.myathena.domain.Photo
import com.tmjee.myathena.domain.Photos
import com.tmjee.myathena.repository.PhotosRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoViewPager2Adapter(fragment: Fragment, private val photos: Photos): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return photos.photo.imageUrls.size
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment(photos.photo, position)
    }

}

class PhotoFragment(private val photo: Photo, private val position: Int): Fragment() {

    lateinit var bindings: ViewPagerFragmentPhotosBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        bindings = ViewPagerFragmentPhotosBinding.inflate(layoutInflater, container, false)
        bindings.imageDescription.text = photo.description
        Picasso
            .get()
            .load(photo.imageUrls[position])
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            .into(bindings.imageView)
        return bindings.root
    }
}

class PhotosDiffCallback: DiffUtil.ItemCallback<Photos>() {
    override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean {
        return oldItem == newItem
    }
}

class PhotosViewHolder(private val binding: RecyclerViewPhotosBinding, private val owningFragment: Fragment): RecyclerView.ViewHolder(binding.root) {
    fun bind(photos: Photos?) {
        println("****** bind photos ${photos}")
        if (photos != null) {
            binding.viewPager.adapter = PhotoViewPager2Adapter(owningFragment, photos)
        }
    }
}


class RecyclerViewAdapter(private val layoutInflater: LayoutInflater, private val owningFragment: Fragment): PagingDataAdapter<Photos, PhotosViewHolder>(PhotosDiffCallback()) {
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        println("**** on bind view holder for recycler view")
        val photos = getItem(position)
        holder.bind(photos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        println("**** on create view holder for recycler view")
        val binding = RecyclerViewPhotosBinding.inflate(layoutInflater, parent, false)
        return PhotosViewHolder(binding, owningFragment)
    }
}

class PhotosPagingSource(private val photosRepo: PhotosRepo, private val accountNumber: String): PagingSource<Int, Photos>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photos> {
        try {
            val page = params.key ?: 1;

            val response = withContext(Dispatchers.IO) {
                photosRepo.getPhotos(accountNumber).execute()
            }
            val photos = response.body()?.photos ?: emptyList()

            return LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch(e: Throwable) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}

class PhotoStateViewHolder(private val binding: RecyclerViewPhotosStateBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.buttonReload.isVisible = if (loadState is LoadState.Error) true else false
        binding.labelLoading.isVisible = if (loadState is LoadState.Loading) true else false
        binding.labelError.isVisible = if (loadState is LoadState.Error) true else false
    }
}


class PhotosLoadStateAdapter(private val layoutInflater: LayoutInflater, private val lifecycleOwner: LifecycleOwner): LoadStateAdapter<PhotoStateViewHolder>() {
    override fun onBindViewHolder(holder: PhotoStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PhotoStateViewHolder {
        val binding = RecyclerViewPhotosStateBinding.inflate(layoutInflater, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return PhotoStateViewHolder(binding)
    }
}