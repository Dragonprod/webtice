/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import ReferenceBookMenu from "../components/Tags";
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
import { renderTagsList } from "../services/renders";
import TagPage from "../components/TagPage";

export const tagsAndProperties = [
  { title: "<!-- -->" },
  { title: "<!DOCTYPE>" },
  { title: "align-content" },
  { title: "align-items" },
];

export default function ReferenceBook() {
  const [tags, setTags] = useState([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    const getTagsApi = async () => {
      const response = await API.get("/tag");
      setTags(response.data);
      setLoading(false);
    };
    getTagsApi();
  }, []);

  const tagsData = renderTagsList(tags);
  return (
    <>
      <ReferenceBookMenu />
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
            <nav
              className={styles.sidebarNavLinks}
              aria-label="secondary mailbox folders"
            >
              <List>{tagsData}</List>
            </nav>
            <h2 className={styles.cssTitle}>CSS</h2>
            <nav
              className={styles.sidebarNavLinks}
              aria-label="secondary mailbox folders"
            >
              <List>
                <ListItem disablePadding>
                  <ListItemButton component="a" href="#align-content">
                    <ListItemText primary="align-content" />
                  </ListItemButton>
                </ListItem>
                <ListItem disablePadding>
                  <ListItemButton component="a" href="#align-items">
                    <ListItemText primary="align-items" />
                  </ListItemButton>
                </ListItem>
              </List>
            </nav>
          </div>
          <div className={styles.mainСontent}>
            {/* <div className={styles.textContainer}>
            <h2 className={styles.themeTitle}>Быстрый старт</h2>
            <p className={styles.text}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Laborum
              nemo obcaecati dolor blanditiis magnam repudiandae in repellendus
              voluptates quibusdam natus?
            </p>
          </div>
          <div className={styles.codeContainer}></div> */}
            <TagPage
              name={tags[3].name}
              description={tags[3].description}
              attrs={tags[3].attributes}
            />
          </div>
        </div>
      )}
    </>
  );
}
