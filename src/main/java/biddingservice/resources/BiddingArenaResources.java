package biddingservice.resources;

import biddingservice.exceptions.IllegalBidArgumentException;
import biddingservice.exceptions.InvalidLotIdArgumentException;
import biddingservice.exceptions.InvalidUpdateException;
import biddingservice.exceptions.LotCreateException;
import biddingservice.objects.request.BrowseLotsRequest;
import biddingservice.objects.request.PlaceBidRequest;
import biddingservice.objects.request.CreateLotRequest;
import biddingservice.objects.response.*;
import biddingservice.services.BiddingArena;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Path("/bid")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class BiddingArenaResources {
    private BiddingArena biddingArena;
    private ObjectMapper objectMapper;

    @Inject
    public BiddingArenaResources(BiddingArena biddingArena, ObjectMapper objectMapper) {
        this.biddingArena = biddingArena;
        this.objectMapper = objectMapper;
    }

    @POST
    @Path("/create-lot")
    public GenericResponse createLot(CreateLotRequest request) throws JsonProcessingException {
        log.info("create-lot: "+objectMapper.writeValueAsString(request));
        try {
            CreateLotResponseBody responseBody = biddingArena.createLot(request);
            log.info("response-body: "+objectMapper.writeValueAsString(responseBody));
            return new GenericResponse(200, responseBody);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    @POST
    @Path("/browse-lots")
    public GenericResponse browseLots(BrowseLotsRequest request) throws JsonProcessingException {
        log.info("browse-lots: "+objectMapper.writeValueAsString(request));
        try {
            BrowseLotsResponseBody responseBody = biddingArena.browseLots(request);
            log.info("response-body: "+objectMapper.writeValueAsString(responseBody));
            return new GenericResponse(200, responseBody);
        } catch (Exception ex) {
            throw  handleException(ex);
        }
    }

    @POST
    @Path("/place-bid")
    public GenericResponse placeBid(PlaceBidRequest request) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(request));
        try {
            PlaceBidResponseBody responseBody = biddingArena.placeBid(request);
            log.info("response-body: "+objectMapper.writeValueAsString(responseBody));
            return new GenericResponse(200, responseBody);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    private WebApplicationException handleException(Exception ex) {
        log.info("Request failed- ", ex);
        if (ex instanceof IllegalBidArgumentException e) {
            GenericResponse genericResponse = new GenericResponse(400, new BadRequestResponse(e.getMessage()));
            return throwRequestResponse(genericResponse, 400);
        } else if (ex instanceof InvalidUpdateException e) {
            GenericResponse genericResponse = new GenericResponse(400, new BadRequestResponse(e.getMessage()));
            return throwRequestResponse(genericResponse, 400);
        } else if (ex instanceof InvalidLotIdArgumentException e) {
            GenericResponse genericResponse = new GenericResponse(400, new BadRequestResponse(e.getMessage()));
            return throwRequestResponse(genericResponse, 400);
        } else if (ex instanceof LotCreateException e) { // this is not bad request right.
            GenericResponse genericResponse = new GenericResponse(400, new BadRequestResponse(e.getMessage()));
            return throwRequestResponse(genericResponse, 400);
        } else {
            GenericResponse genericResponse = new GenericResponse(500, new ServerDownResponseBody(ex.getMessage()));
            return throwRequestResponse(genericResponse, 500);
        }
    }

    private WebApplicationException throwRequestResponse(GenericResponse genericResponse, int status) throws WebApplicationException {
        return new WebApplicationException(Response
                .status(status)
                .entity(genericResponse)
                .type(MediaType.APPLICATION_JSON)
                .build());
    }

}
