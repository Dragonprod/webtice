/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from "react";
import Menu from "../components/ReferenceMenu";
import styles from "../styles//ReferenceBookPage.module.css";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";
import Autocomplete from "@mui/material/Autocomplete";
import CircularProgress from "@mui/material/CircularProgress";
import API from "../api/api";
import TagPage from "../components/TagPage";
import CodeEditor from "../components/CodeEditor";
import Skeleton from '@mui/material/Skeleton';

const ReferenceBook = () => {
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
        <>
          {/* <CircularProgress /> */}
          <div className={styles.mainGrid}>
            <div className={styles.sidebar}>
              <Skeleton variant="rectangular" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '20px' }} />
              <Skeleton variant="text" height={60} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', marginTop: '10px', width: '70%' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />

              <Skeleton variant="text" height={60} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', marginTop: '10px', width: '70%' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', margin: '-4px 0 0 30px' }} />
            </div>
            <div className={styles.mainСontent}>
              <Skeleton variant="text" width={300} height={70} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '10px', marginTop: '-13px' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '5px', margin: '-8px 0' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '5px', margin: '-8px 0' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '5px', margin: '-8px 0' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '5px', margin: '-8px 0' }} />
              <Skeleton variant="text" height={40} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '5px', margin: '-8px 0' }} />
              <Skeleton variant="rectangular" height={500} sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '20px', marginTop: '40px' }} />
            </div>
          </div>
        </>
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
              <React.Fragment>
                <List>{tagsData}</List>
              </React.Fragment>
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
          <div className={styles.mainСontent}>
            {tagPage}
            {/* <CodeEditor /> */}
          </div>
        </div>
      )}
    </>
  );
};
export default ReferenceBook;
