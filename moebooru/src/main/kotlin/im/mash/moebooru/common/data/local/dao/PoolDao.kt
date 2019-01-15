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

package im.mash.moebooru.common.data.local.dao

import androidx.room.*
import im.mash.moebooru.common.data.local.entity.Pool
import io.reactivex.Flowable

@Dao
interface PoolDao {

    @Query("SELECT * FROM pools WHERE host = :host ORDER BY id DESC")
    fun getPools(host: String): Flowable<MutableList<Pool>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBoorus(pools: MutableList<Pool>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooru(pool: Pool)

    @Delete
    fun deletePool(pool: Pool)

    @Delete
    fun deletePools(pools: MutableList<Pool>)

    @Query("DELETE FROM pools WHERE host = :host AND id NOT IN (SELECT id FROM pools WHERE host = :host ORDER BY id DESC LIMIT :limit)")
    fun deletePools(host: String, limit: Int)
}