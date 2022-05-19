package ru.mirea.webtice.backend.dto.models;

public class StartTest {

    private Long user_id;
    private Long test_id;

    public Long getTest_id() {
        return test_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setTest_id(Long test_id) {
        this.test_id = test_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
