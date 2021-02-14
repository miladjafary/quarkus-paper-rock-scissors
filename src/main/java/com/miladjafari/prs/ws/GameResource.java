package com.miladjafari.prs.ws;

import com.miladjafari.prs.sdk.dto.CreateGameResponseDto;
import com.miladjafari.prs.sdk.dto.GameResultDto;
import com.miladjafari.prs.sdk.exception.GameRuntimeException;
import com.miladjafari.prs.service.GameManagerService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

@Path("/")
public class GameResource {

    private static final Logger logger = Logger.getLogger(GameResource.class.getName());

    @Inject
    GameManagerService gameManagerService;

    @POST
    @Path("/games/create/singlePlayer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewSinglePlayerGame(@Context UriInfo uriInfo) {
        CreateGameResponseDto createGameResponse = gameManagerService.createSinglePlayerGame();
        return Response.created(uriInfo.getBaseUri()).entity(createGameResponse).build();
    }

    @PUT
    @Path("/games/{gameId}/{playerId}/play/{symbolId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(
            @Context UriInfo uriInfo,
            @PathParam("gameId") String gameId,
            @PathParam("playerId") String playerId,
            @PathParam("symbolId") Integer symbolId
    ) {
        try {
            GameResultDto gameResult = gameManagerService.play(gameId, playerId, symbolId);

            return Response.ok(gameResult).build();
        } catch (GameRuntimeException exception) {
            logger.severe(exception.getMessage());
            JsonObject errorResponse = Json.createObjectBuilder()
                                           .add("error", exception.getMessage())
                                           .build();
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(errorResponse)
                           .build();
        }
    }
}