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
        CardEntity cardId = cardRepo.findByAccountId(request.getAccountId());
        CardEntity cardEntity = new CardEntity().builder()
                .accountId(request.getAccountId())
                .build();
        if(cardId == null){
            cardRepo.save(cardEntity);
        }else{
            cardInfoRepo.deleteByCardId(cardId.getCardId());
            cardRepo.deleteById(cardId.getCardId());
            cardRepo.save(cardEntity);
        }
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        statusResponse.setCode("000");
        statusResponse.setMessage("Success");
        response.setStatus(statusResponse);
        return response;
    }

    public List<CardResponse> getCard(GetAllRequest cardRequest) {
        CardEntity cardId = cardRepo.findByAccountId(cardRequest.getAccountId());
        List<CardInfoEntity> cardInfoEntities = cardInfoRepo.findAllByCardId(cardId.getCardId());
        List<CardResponse> cardResponses = new ArrayList<>();
        for (CardInfoEntity cardInfoEntity: cardInfoEntities
             ) {
            Optional<BookEntity> book = bookRepo.findById(cardInfoEntity.getBookId());
            CardResponse card = new CardResponse().builder()
                    .bookId(book.get().getBookId())
                    .bookName(book.get().getBookName())
                    .numberBook(cardInfoEntity.getNumberBook())
                    .bookInfo(book.get().getBookInfo())
                    .bookPrice(book.get().getBookPrice())
                    .bookType(book.get().getBookType())
                    .build();
            cardResponses.add(card);
        }
        return cardResponses;
    }


    public double payCard(GetAllRequest cardRequest) {
        CardEntity cardId = cardRepo.findByAccountId(cardRequest.getAccountId());
        List<CardInfoEntity> cardInfoEntities = cardInfoRepo.findAllByCardId(cardId.getCardId());
        double price= 0;
        for (CardInfoEntity c: cardInfoEntities
        ) {
            Optional<BookEntity> book = bookRepo.findById(c.getBookId());
            price += Double.parseDouble(book.get().getBookPrice())  *  Double.parseDouble(c.getNumberBook().toString());
        }
        return price;
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
        CardEntity cardId = cardRepo.findByAccountId(cardRequest.getAccountId());
        GeneralResponse response = new GeneralResponse();
        GeneralResponse.StatusResponse statusResponse = new GeneralResponse.StatusResponse();
        if (cardId == null) {
            statusResponse.setCode("001");
            statusResponse.setMessage("Can't find cardId");
            response.setStatus(statusResponse);
            return response;
        } else {
            if (action.equals("update")) {
                cardInfoRepo.updateBook(cardRequest.getNumberBook(),cardRequest.getBookId(),cardId.getCardId());
            } else if (action.equals("delete")) {
                cardInfoRepo.deleteByCardIdAndBookId(cardId.getCardId(),cardRequest.getBookId());
            }else if (action.equals("add")) {
                CardInfoEntity entity = cardInfoRepo.findByBookIdAndCardId(cardRequest.getBookId(),cardId.getCardId());
                if(entity != null) {
                    cardRequest.setNumberBook(entity.getNumberBook() + cardRequest.getNumberBook());
                    actioncard(cardRequest,"update");
                }else{
                    cardInfoRepo.addBook(cardId.getCardId(),cardRequest.getBookId(),cardRequest.getNumberBook());
                }
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
