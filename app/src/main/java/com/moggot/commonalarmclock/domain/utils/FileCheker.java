package com.moggot.commonalarmclock.domain.utils;

public class FileCheker {

    private enum SupportedFileFormat {
        _3GP("3gp"),
        MP4("mp4"),
        M4A("m4a"),
        AAC("aac"),
        TS("ts"),
        FLAC("flac"),
        MP3("mp3"),
        MID("mid"),
        XMF("xmf"),
        MXMF("mxmf"),
        RTTTL("rtttl"),
        RTX("rtx"),
        OTA("ota"),
        IMY("imy"),
        OGG("ogg"),
        MKV("mkv"),
        WAV("wav");

        private String filesuffix;

        SupportedFileFormat(String filesuffix) {
            this.filesuffix = filesuffix;
        }

        public String getFilesuffix() {
            return filesuffix;
        }
    }

    static public boolean checkExtension(String fileName) {
        String ext = getFileExtension(fileName);
        if (ext == null) return false;
        try {
            if (SupportedFileFormat.valueOf(ext.toUpperCase()) != null) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    static private String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else
            return null;
    }
}
