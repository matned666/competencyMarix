package eu.mrndesign.www.matned.service;

import eu.mrndesign.www.matned.dto.DTOEintityDescriptionImplementation;
import eu.mrndesign.www.matned.model.common.EntityDescriptionImplementation;
import eu.mrndesign.www.matned.model.common.EntityDescription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BaseService<E> {

    @Value("${default.sort.by}")
    protected String defaultSortBy;
    @Value("${default.page.start}")
    protected Integer defaultStartPage;
    @Value("${default.page.size}")
    protected Integer defaultPageSize;

    protected EntityDescription getEntityDescription(EntityDescriptionImplementation entity, DTOEintityDescriptionImplementation dto) {
        EntityDescription entityDescription;
        if (entity.getEntityDescription() == null)
            entityDescription = new EntityDescription(dto.getName(), dto.getDescription());
        else {
            entityDescription = entity.getEntityDescription();
            entityDescription.setName(dto.getName());
            entityDescription.setDescription(dto.getDescription());
        }
        return entityDescription;
    }


    protected Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    protected Pageable getPageable(Integer startPage, Integer itemsPerPage, String[] sortBy) {
        if (itemsPerPage == null)
            itemsPerPage = defaultPageSize;
        if (startPage == null)
            startPage = defaultStartPage;
        if (itemsPerPage < 1)
            itemsPerPage = 1;
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortElement : sortBy) {
            if (sortElement.contains(",")) {
                String[] _order = sortElement.split(",");
                orders.add(new Sort.Order(getSortDirection(_order[1]), _order[0]));
            } else {
                if (sortElement.equals("desc") || sortElement.equals("asc")) {
                    String _sortBy = orders.get(orders.size() - 1).getProperty();
                    orders.remove(orders.size() - 1);
                    orders.add(new Sort.Order(getSortDirection(sortElement), _sortBy));
                } else orders.add(new Sort.Order(getSortDirection("asc"), sortElement));
            }
        }
        Pageable pageable;
        try {
            pageable = PageRequest.of(startPage, itemsPerPage, Sort.by(orders));
        } catch (Exception e) {
            e.printStackTrace();
            pageable = PageRequest.of(startPage, itemsPerPage,
                    Sort.by(new Sort.Order(getSortDirection("asc"), "id")));
        }
        return pageable;
    }

    protected List<E> getDTOsSortedByRelevance(String searchedValue, List<E> list, String type) {
        List<E> sortedListBySearchRelevance = new LinkedList<>();
        sortedListBySearchRelevance.addAll(
                list.stream()
                        .filter(x -> getSortingFilter(x, searchedValue, type))
                        .collect(Collectors.toList())
        );
        sortedListBySearchRelevance.addAll(
                list.stream()
                        .filter(x -> !getSortingFilter(x, searchedValue, type))
                        .collect(Collectors.toList())
        );
        return sortedListBySearchRelevance;
    }

    protected boolean getSortingFilter(E x, String searchedValue, String type) {
        return true;
    }

    protected boolean isChanged(String str) {
        if (str != null)
            return !str.trim().isEmpty() && !str.trim().isBlank();
        else return false;
    }


}
