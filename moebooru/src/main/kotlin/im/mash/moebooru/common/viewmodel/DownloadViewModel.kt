/*
 * Copyright (C) 2019 by onlymash <im@fiepi.me>, All rights reserved
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package im.mash.moebooru.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import im.mash.moebooru.common.data.local.entity.PostDownload
import im.mash.moebooru.core.extensions.toLiveData
import im.mash.moebooru.core.scheduler.Outcome
import im.mash.moebooru.common.model.DownloadDataContract
import io.reactivex.disposables.CompositeDisposable

class DownloadViewModel(private val repo: DownloadDataContract.Repository,
                        private val compositeDisposable: CompositeDisposable) : ViewModel() {

    companion object {
        private const val TAG = "DownloadViewModel"
    }

    val downloadPostsOutcome: LiveData<Outcome<MutableList<PostDownload>>> by lazy {
        repo.downloadPostsOutcome.toLiveData(compositeDisposable)
    }

    fun loadAll() {
        repo.loadPosts()
    }

    fun delete(post: PostDownload) {
        repo.deletePost(post)
    }

    fun delete(posts: MutableList<PostDownload>) {
        repo.deletePosts(posts)
    }

    fun deleteAll() {
        repo.deleteAll()
    }

    fun addTask(post: PostDownload) {
        repo.addPost(post)
    }

    override fun onCleared() {
        super.onCleared()
        //clear the disposables when the viewmodel is cleared
        compositeDisposable.clear()
    }
}