package ru.skillbox.socialnetwork.account.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.socialnetwork.account.dto.AccountByFilterDTO;
import ru.skillbox.socialnetwork.account.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> byRequest(AccountByFilterDTO req) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (req.getIds() != null && !req.getIds().isEmpty())
                predicates.add(root.get("id").in(req.getIds()));

            if (req.getAuthor() != null)
                predicates.add(criteriaBuilder.equal(root.get("author"), req.getAuthor()));

            if (req.getFirstName() != null)
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + req.getFirstName() + "%"));

            if (req.getLastName() != null)
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + req.getLastName() + "%"));

            if (req.getCity() != null)
                predicates.add(criteriaBuilder.equal(root.get("city"), req.getCity()));

            if (req.getCountry() != null)
                predicates.add(criteriaBuilder.equal(root.get("country"),req.getCountry()));

            if (req.getIsBlocked() != null)
                predicates.add(criteriaBuilder.equal(root.get("isBlocked"),req.getIsBlocked()));

            if (req.getIsDeleted() != null)
                predicates.add(criteriaBuilder.equal(root.get("isDeleted"),req.getIsDeleted()));

            if (req.getAgeTo() != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ageTo"),req.getAgeTo()));

            if (req.getAgeFrom() != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ageFrom"),req.getAgeFrom()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
