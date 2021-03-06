package edu.ggranados.rewardpoints.api.service;

import edu.ggranados.rewardpoints.api.entity.Transaction;
import edu.ggranados.rewardpoints.api.entity.TransactionPage;
import edu.ggranados.rewardpoints.api.entity.TransactionSearchCriteria;
import edu.ggranados.rewardpoints.api.repository.TransactionCriteriaRepository;
import edu.ggranados.rewardpoints.api.repository.TransactionRepository;
import edu.ggranados.rewardpoints.api.response.TransactionResponseForRewards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found: {0}";
    public static final String NO_TRANSACTIONS_WITH_ID = "No transactions with id {}";

    private final TransactionRepository transactionRepository;
    private final TransactionCriteriaRepository transactionCriteriaRepositoy;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionCriteriaRepository transactionCriteriaRepositoy) {
        this.transactionRepository = transactionRepository;
        this.transactionCriteriaRepositoy = transactionCriteriaRepositoy;
    }


    @Override
    public ArrayList<TransactionResponseForRewards> findAll() {
        logger.debug("Getting all transactions");
        return transactionRepository.findAll()
                .stream()
                .map(TransactionResponseForRewards::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<TransactionResponseForRewards> findAllApplicableTransactionsByClient(String clientId) {
        logger.debug("Getting all applicable transactions by client {}", clientId);
        return transactionRepository.findTransactionByClientIdAndApplicable(clientId, Boolean.TRUE)
                .stream()
                .map(TransactionResponseForRewards::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public ArrayList<TransactionResponseForRewards> saveTransaction(Transaction transaction) {
        logger.debug("Saving transaction {}", transaction);
        ArrayList<TransactionResponseForRewards> list = new ArrayList<>();
        Transaction t = transactionRepository.save(transaction);
        list.add(new TransactionResponseForRewards(t));
        return list;
    }

    @Override
    public ArrayList<TransactionResponseForRewards> findTransaction(Long transactionId)
            throws ResponseStatusException{
        logger.debug("Getting transactions with id {}", transactionId);
        Optional<Transaction> t = transactionRepository.findById(transactionId);
        if(!t.isPresent()){
            logger.debug(NO_TRANSACTIONS_WITH_ID, transactionId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageFormat.format(TRANSACTION_NOT_FOUND, transactionId));
        }
        ArrayList<TransactionResponseForRewards> list = new ArrayList<>();
        list.add(new TransactionResponseForRewards(t.get()));
        return list;
    }

    @Override
    public ArrayList<TransactionResponseForRewards> removeTransaction(Long transactionId) throws ResponseStatusException{
        logger.debug("Removing transactions with id {}", transactionId);
        Optional<Transaction> t = transactionRepository.findById(transactionId);
        if(!t.isPresent()){
            logger.debug(NO_TRANSACTIONS_WITH_ID, transactionId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageFormat.format(TRANSACTION_NOT_FOUND, transactionId));
        }
        transactionRepository.delete(t.get());

        return new ArrayList<>();
    }

    @Override
    public ArrayList<TransactionResponseForRewards> editTransaction(Transaction transaction, Long transactionId) {
        logger.debug("Editing transactions with id {}", transaction);
        Optional<Transaction> t = transactionRepository.findById(transactionId);
        if(!t.isPresent()){
            logger.debug(NO_TRANSACTIONS_WITH_ID, transaction.getId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessageFormat.format(TRANSACTION_NOT_FOUND, transaction.getId()));
        }
        transactionRepository.save(transaction);

        ArrayList<TransactionResponseForRewards> list = new ArrayList<>();
        list.add(new TransactionResponseForRewards(t.get()));
        return list;
    }

    @Override
    public Page<Transaction> getTransactions(TransactionPage page,
                                             TransactionSearchCriteria searchCriteria){
        return transactionCriteriaRepositoy.findAllWithFilters(page, searchCriteria);
    }
}
