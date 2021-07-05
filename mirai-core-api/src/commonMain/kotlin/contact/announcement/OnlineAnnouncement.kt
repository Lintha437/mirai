/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/dev/LICENSE
 */

@file:Suppress("INAPPLICABLE_JVM_NAME", "NOTHING_TO_INLINE")
@file:JvmBlockingBridge

package net.mamoe.mirai.contact.announcement

import net.mamoe.kjbb.JvmBlockingBridge
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.PermissionDeniedException
import net.mamoe.mirai.utils.NotStableForInheritance
import java.time.Instant


/**
 * 表示从 [Announcements.get] 等途径在线获取的, 已经存在于服务器的公告.
 *
 * [OnlineAnnouncement] 拥有唯一识别属性 [fid] 代表其存在于服务器中的 ID. 可进行 [删除][delete]
 *
 * @since 2.7
 */
@NotStableForInheritance
public interface OnlineAnnouncement : Announcement {
    /**
     * 公告所属群
     */
    public val group: Group

    /**
     * 公告发送者 [NormalMember.id]
     */
    public val senderId: Long

    /**
     * 公告发送者. 当该成员已经离开群后为 `null`
     */
    public val sender: NormalMember?

    /**
     * 唯一识别属性
     */
    public val fid: String

    /**
     * 所有人都已阅读, 如果 [AnnouncementParameters.needConfirm] 为 `true` 则为所有人都已确认.
     */
    public val isAllRead: Boolean

    /**
     * 已经阅读的成员数量，如果 [AnnouncementParameters.needConfirm] 为 `true` 则为已经确认的成员数量
     */
    public val readMemberNumber: Int

    /**
     * 公告发出的时间，为 EpochSecond (自 1970-01-01T00：00：00Z 的秒数)
     *
     * @see Instant.ofEpochSecond
     */
    public val publishTime: Long

    /**
     * 删除这个公告. 需要管理员权限.
     *
     * @return 成功返回 `true`, 群公告已被删除时返回 `false`
     * @throws PermissionDeniedException 没有权限时抛出
     * @see Announcements.delete
     */
    public suspend fun delete(): Boolean = group.announcements.delete(fid)
}

/**
 * 公告所属 [Bot], 即 `sender.bot`.
 * @since 2.7
 */
public inline val OnlineAnnouncement.bot: Bot get() = group.bot
