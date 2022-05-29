package at.fhv.ec.communication.rest;

import at.fhv.ec.application.api.DownloadSongService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Path("/songs")
public class SongController {
    @Inject
    DownloadSongService downloadSongService;

    @GET
    @Path("/{username}/{songId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "404", description = "When the song cannot be found.")
    @APIResponseSchema(value = byte[].class, responseCode = "200")
    @Operation(operationId = "getSong", description = "Responds with the MP3 of the song as bytes")
    public Response getSong(@PathParam("username") String username, @PathParam("songId") UUID songId) {
        try {
            byte[] songFile = downloadSongService.downloadSong(username, songId);
            return Response.ok(songFile)
                    .header("Content-Disposition", "attachment;filename=example.mp3")
                    .build();
        } catch (NoSuchElementException | IOException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
