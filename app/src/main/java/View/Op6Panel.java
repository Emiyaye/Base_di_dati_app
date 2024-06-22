package View;

import java.util.List;

import data.Dati;

public class Op6Panel extends GeneralSelectPanel<Dati.Op6Data> {

    public Op6Panel() {
        super("Account",
                List.of(
                        new Table<>("Artist", new String[] { "ArtistiSeguiti" }, Op6Panel::artistFunction),
                        new Table<>("Album", new String[] { "AlbumSeguiti", "AutoreAlbum" }, Op6Panel::albumFunction),
                        new Table<>("Playlist", new String[] { "PlaylistSeguiti", "AutorePlaylist" },
                                Op6Panel::playlistFunction)));
    }

    private static Object[] artistFunction(Dati.Op6Data data) {
        return new Object[] { data.NomeArtista() };
    }

    private static Object[] albumFunction(Dati.Op6Data data) {
        return new Object[] { data.NomeAlbum(), data.NomeAutore() };
    }

    private static Object[] playlistFunction(Dati.Op6Data data) {
        return new Object[] { data.NomePlaylist(), data.AutorePlaylist() };
    }
}
