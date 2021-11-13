/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import Menu from "../components/ReferenceMenu";
import styles from "../styles//ReferenceBookPage.module.css";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import Autocomplete from "@mui/material/Autocomplete";
import CircularProgress from "@mui/material/CircularProgress";
import API from "../api/api";
import TagPage from "../components/TagPage";

export default function ReferenceBook() {
  const [tags, setTags] = useState([]);
  const [loading, setLoading] = useState(true);
  const [tagPage, settagPage] = useState();

  useEffect(() => {
    const getTagsApi = async () => {
      const response = await API.get("/tag");
      setTags(response.data);
      settagPage(
        <TagPage
          name={response.data[0].name}
          description={response.data[0].description}
          attrs={response.data[0].attributes}
        />
      );
      setLoading(false);
    };
    getTagsApi();
  }, []);

  const handleItemChange = (tag) => {
    settagPage(
      <TagPage
        name={tag.name}
        description={tag.description}
        attrs={tag.attributes}
      />
    );
  };

  const tagsData = tags.map((tag) => (
    <ListItem
      key={tag.id}
      onClick={() => {
        handleItemChange(tag);
      }}
    >
      <ListItemText primary={tag.name} />
    </ListItem>
  ));

  return (
    <>
      <Menu />
      {loading && (
        <div className={styles.mainСontent}>
          <CircularProgress />
        </div>
      )}
      {!loading && (
        <div className={styles.mainGrid}>
          <div className={styles.sidebar}>
            <Stack spacing={2} sx={{ width: 240 }}>
              <Autocomplete
                className={styles.searchbar}
                id="search-main"
                freeSolo
                options={tags.map((option) => option.name)}
                renderInput={(params) => (
                  <TextField {...params} label="Поиск" />
                )}
              />
            </Stack>
            <h2 className={styles.htmlTitle}>HTML</h2>
            <nav className={styles.sidebarNavLinks} aria-label="html tags">
              <List>{tagsData}</List>
            </nav>
            <h2 className={styles.cssTitle}>CSS</h2>
            <nav className={styles.sidebarNavLinks} aria-label="css tags">
              <List>
                <ListItem>
                  <ListItemText primary="css-tag" />
                </ListItem>
              </List>
            </nav>
          </div>
          <div className={styles.mainСontent}>{tagPage}</div>
        </div>
      )}
    </>
  );
}
