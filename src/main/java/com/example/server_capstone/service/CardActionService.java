package com.example.server_capstone.service;

import com.example.server_capstone.dto.request.CardRequest;
import com.example.server_capstone.dto.request.GetAllRequest;
import com.example.server_capstone.dto.response.CardResponse;
import com.example.server_capstone.dto.response.GeneralResponse;
import com.example.server_capstone.dto.response.ListResponse;
import com.example.server_capstone.entity.BookEntity;
import com.example.server_capstone.entity.CardEntity;
import com.example.server_capstone.entity.CardInfoEntity;
import com.example.server_capstone.repository.BookRepo;
import com.example.server_capstone.repository.CardInfoRepo;
import com.example.server_capstone.repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardActionService {
    @Autowired
    CardRepo cardRepo;
    @Autowired
    CardInfoRepo cardInfoRepo;
    @Autowired
    BookRepo bookRepo;

    public GeneralResponse createCard(CardRequest request){
        Long cardId = cardRepo.findByAccountId(request.getAccountId());
        CardEntity cardEntity = new CardEntity().builder()
                .accountId(request.getAccountId())
                .build();
        if(cardId == null){
            cardRepo.save(cardEntity);
        }else{
            cardRepo.deleteById(cardId);
            cardRepo.save(cardEntity);
        }
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        statusResponse.setCode("000");
        statusResponse.setMessage("Success");
        response.setStatus(statusResponse);
        return response;
    }

    public ListResponse<CardResponse> getCard(GetAllRequest cardRequest) {
        Long cardId = cardRepo.findByAccountId(cardRequest.getAccountId());
        List<CardInfoEntity> cardInfoEntities = cardInfoRepo.findAllByCardId(cardId);
        List<CardResponse> cardResponses = new ArrayList<>();
        for (CardInfoEntity cardInfoEntity: cardInfoEntities
             ) {
            Optional<BookEntity> book = bookRepo.findById(cardInfoEntity.getBookId());
            CardResponse card = new CardResponse().builder()
                    .bookName(book.get().getBookName())
                    .numberBook(cardInfoEntity.getNumberBook())
                    .build();
            cardResponses.add(card);
        }
        ListResponse<CardResponse> cardResponseListResponse = new ListResponse<>();
        cardResponseListResponse.setList(cardResponses);
        return cardResponseListResponse;
    }

    public GeneralResponse updateCard(CardRequest cardRequest) {
        return actioncard(cardRequest,"update");
    }

    public GeneralResponse deleteCard(CardRequest cardRequest) {
        return actioncard(cardRequest,"delete");
    }

    public GeneralResponse addCard(CardRequest cardRequest) {
        return actioncard(cardRequest,"add");
    }

    GeneralResponse actioncard(CardRequest cardRequest, String action) {
        Long cardId = cardRepo.findByAccountId(cardRequest.getAccountId());
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (cardId == null) {
            statusResponse.setCode("001");
            statusResponse.setMessage("Can't find cardId");
            response.setStatus(statusResponse);
            return response;
        } else {
            if (action.equals("update")) {
                cardInfoRepo.updateBook(cardRequest.getNumberBook(),cardRequest.getBookId(),cardId);
            } else if (action.equals("delete")) {
                cardInfoRepo.deleteByCardIdAndBookId(cardId,cardRequest.getBookId());
            }else if (action.equals("add")) {
                cardInfoRepo.addBook(cardId,cardRequest.getBookId(),cardRequest.getNumberBook());
            }else {
                statusResponse.setCode("002");
                statusResponse.setMessage("Wrong action !");
                response.setStatus(statusResponse);
                return response;
            }
            statusResponse.setCode("000");
            statusResponse.setMessage("Success");
            response.setStatus(statusResponse);
            return response;
        }


    }
}
