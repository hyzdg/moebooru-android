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

package im.mash.moebooru.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

inline fun <reified M : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): M {
    return ViewModelProvider(this, viewModelFactory).get(M::class.java)
}

inline fun <reified M : ViewModel> AppCompatActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): M {
    return ViewModelProvider(this, viewModelFactory).get(M::class.java)
}

inline fun <reified M : ViewModel> Fragment.getViewModel(): M {
    val application = activity?.application
            ?: throw IllegalStateException("Fragment is not attached to activity")
    val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    return ViewModelProvider(this, factory).get(M::class.java)
}

inline fun <reified M : ViewModel> AppCompatActivity.getViewModel(): M {
    val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    return ViewModelProvider(this, factory).get(M::class.java)
}