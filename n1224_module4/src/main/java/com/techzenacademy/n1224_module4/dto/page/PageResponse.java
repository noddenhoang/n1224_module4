package com.techzenacademy.n1224_module4.dto.page;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    List<T> content;
    PageCustom<T> page;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.page = new PageCustom<>(page);
    }
}
