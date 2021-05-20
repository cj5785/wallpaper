package com.pickni.wallpaper.common;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * date        : 2021/5/20 14:53
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
@Entity
public class WallpaperData {
    @Id
    public long id;
    public String path;
}
