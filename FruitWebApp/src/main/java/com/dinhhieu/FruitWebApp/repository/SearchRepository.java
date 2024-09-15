package com.dinhhieu.FruitWebApp.repository;

import com.dinhhieu.FruitWebApp.dto.response.PageResponse;
import com.dinhhieu.FruitWebApp.model.Customer;
import com.dinhhieu.FruitWebApp.repository.criteria.CustomerSearchCriteriaConsumer;
import com.dinhhieu.FruitWebApp.repository.criteria.SearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Component
public class SearchRepository {
    @PersistenceContext // lam viec voi jpa va hibernate
    private EntityManager entityManager;


    public PageResponse<?> getALlCustomerWithSortByColumnAndSearch(int pageNo, int pageSize, String search,String sortBy){
        // query list customer
         StringBuilder sqlQuery = new StringBuilder("SELECT c FROM Customer c where 1=1");

         if(StringUtils.hasLength(search)){
            sqlQuery.append(" and lower(c.firstName) like lower(:firstName)");
            sqlQuery.append(" and lower(c.lastName) like lower(:lastName)");
            sqlQuery.append(" and lower(c.email) like lower(:email)");
         }
        Query selectQuery  = entityManager.createQuery(sqlQuery.toString());

        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasLength(search)){
            selectQuery.setParameter("firstName", String.format("%%%s%%", search));
            selectQuery.setParameter("lastName", String.format("%%%s%%", search));
            selectQuery.setParameter("email", String.format("%%%s%%", search));
        }
        List customers = selectQuery.getResultList();

        System.out.println(customers);

        //query list record
        StringBuilder sqlCountQuery = new StringBuilder("SELECT count(*) FROM Customer c where 1=1");

        if(StringUtils.hasLength(search)){
            sqlCountQuery.append(" and lower(c.firstName) like lower(?1)");
            sqlCountQuery.append(" and lower(c.lastName) like lower(?2)");
            sqlCountQuery.append(" and lower(c.email) like lower(?3)");
        }

        Query selectCountQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)){
            selectCountQuery.setParameter(1, String.format("%%%s%%", search));
            selectCountQuery.setParameter(2, String.format("%%%s%%", search));
            selectCountQuery.setParameter(3, String.format("%%%s%%", search));
        }
        Long totalElements = (Long) selectCountQuery.getSingleResult();
        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .total(totalElements)
                .items(customers)
        .build();
    }

    public PageResponse<?> advanceSearchCustomer(int pageNo, int pageSize, String sortBy, String... search){
        // lay ra danh sach customer
        List<SearchCriteria> criteria = new ArrayList<>();
        if(search != null){
            for(String s : search){
                Pattern pattern  = Pattern.compile("(\\w+)(:|>|<)(.*)");
                Matcher matcher = pattern.matcher(s);
                if(matcher.find()){
                    criteria.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        //lay ra danh sach ban ghi
        List<Customer> customers = getCustomers(pageNo, pageSize,criteria,sortBy);
        return PageResponse.builder()
                .page(pageNo)
                .size(pageSize)
                .total(0)
                .items(customers)
                .build();
    }

    private List<Customer> getCustomers(int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query =   criteriaBuilder.createQuery(Customer.class);

        Root<Customer> root = query.from(Customer.class);

        // fix dieu kien search
        Predicate predicate = criteriaBuilder.conjunction();

        CustomerSearchCriteriaConsumer customerSearchCriteriaConsumer = new CustomerSearchCriteriaConsumer(criteriaBuilder,predicate,root);

        criteriaList.forEach(customerSearchCriteriaConsumer);

        predicate = customerSearchCriteriaConsumer.getPredicate();

        query.where(predicate);

        //sort
        if(StringUtils.hasLength(sortBy)){
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if(matcher.find()){
//                criteriaList.add(new SearchCriteria(matcher.group(1),matcher.group(2), matcher.group(3)));
                String columnName = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("desc"))
                    query.orderBy(criteriaBuilder.desc(root.get(columnName)));
                else
                    query.orderBy(criteriaBuilder.asc(root.get(columnName)));
            }
        }

        return  entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
    }

}
