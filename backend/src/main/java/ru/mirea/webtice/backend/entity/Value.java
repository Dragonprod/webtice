package ru.mirea.webtice.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="value_name")
    private String valueName;

    @Column(name="description", length = 1024)
    private String description  ;

    @ManyToOne
    @JoinColumn(name="style_id")
    @JsonBackReference
    private Style style;

    public String getValueName() {
        return valueName;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public Style getStyle() {
        return style;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
}
