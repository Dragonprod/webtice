/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from 'react';
import Menu from '../components/ReferenceMenu';
import styles from '../styles//ReferenceBookPage.module.css';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Autocomplete from '@mui/material/Autocomplete';
import API from '../api/api';
import TagPage from '../components/TagPage';
import Skeleton from '@mui/material/Skeleton';
import CssPage from '../components/CssPage';

import ListItemButton from '@mui/material/ListItemButton';
import Collapse from '@mui/material/Collapse';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';

function findHtmlTagByName(name, htmlTags) {
  for (let i = 0; i < htmlTags.length; i++)
    if (htmlTags[i].name === name) return htmlTags[i];
}

function findCssTagByName(name, cssTags) {
  for (let i = 0; i < cssTags.length; i++)
    if (cssTags[i].styleName === name) return cssTags[i];
}

const ReferenceBook = () => {
  const [htmlTags, sethtmlTags] = useState([]);
  const [cssTags, setcssTags] = useState([]);
  const [loading, setLoading] = useState(true);
  const [htmlPage, sethtmlPage] = useState(true);
  const [htmlTagPage, sethtmlTagPage] = useState();
  const [cssTagPage, setcssTagPage] = useState();
  const [openHtml, setopenHtml] = useState(false);
  const [openCss, setopenCss] = useState(false);

  useEffect(() => {
    const getTagsData = async () => {
      const htmlResponse = await API.get('/tag');
      sethtmlTags(htmlResponse.data);
      sethtmlTagPage(
        <TagPage
          name={htmlResponse.data[0].name}
          description={htmlResponse.data[0].description}
          attrs={htmlResponse.data[0].attributes}
        />
      );

      const cssResponse = await API.get('/style');
      setcssTags(cssResponse.data);

      setLoading(false);
    };

    getTagsData();
  }, []);

  const handleSearchChange = (event, param) => {
    if (param !== undefined && param !== null && param !== '') {
      if (param.startsWith('<')) {
        const htmlTag = findHtmlTagByName(param, htmlTags);
        if (htmlTag !== undefined) {
          sethtmlTagPage(
            <TagPage
              name={htmlTag.name}
              description={htmlTag.description}
              attrs={htmlTag.attributes}
            />
          );
          sethtmlPage(true);
        }
      } else {
        const cssTag = findCssTagByName(param, cssTags);
        if (cssTag !== undefined) {
          setcssTagPage(
            <CssPage
              name={cssTag.styleName}
              description={cssTag.description}
              syntax={cssTag.syntax}
              values={cssTag.values}
            />
          );
          sethtmlPage(false);
        }
      }
    }
  };

  const handleOpenHtmlChange = () => {
    setopenHtml(!openHtml);
  };

  const handleOpenCssChange = () => {
    setopenCss(!openCss);
  };

  const handleHtmlItemChange = htmlTag => {
    sethtmlTagPage(
      <TagPage
        name={htmlTag.name}
        description={htmlTag.description}
        attrs={htmlTag.attributes}
      />
    );
    sethtmlPage(true);
  };

  const handleCssItemChange = cssTag => {
    setcssTagPage(
      <CssPage
        name={cssTag.styleName}
        description={cssTag.description}
        syntax={cssTag.syntax}
        values={cssTag.values}
      />
    );
    sethtmlPage(false);
  };

  const htmlTagsData = htmlTags.map(htmlTag => (
    <ListItem
      key={htmlTag.id}
      onClick={() => {
        handleHtmlItemChange(htmlTag);
      }}>
      <ListItemText primary={htmlTag.name} />
    </ListItem>
  ));

  const cssTagsData = cssTags.map(cssTag => (
    <ListItem
      key={cssTag.id}
      onClick={() => {
        handleCssItemChange(cssTag);
      }}>
      <ListItemText primary={cssTag.styleName} />
    </ListItem>
  ));

  return (
    <>
      <Menu />
      {loading && (
        <>
          <div className={styles.mainGrid}>
            <div className={styles.sidebar}>
              <Skeleton
                variant='rectangular'
                height={40}
                sx={{ bgcolor: 'rgba(255,255,255,.1)', borderRadius: '20px' }}
              />
              <Skeleton
                variant='text'
                height={60}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  marginTop: '10px',
                  width: '70%',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />

              <Skeleton
                variant='text'
                height={60}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  marginTop: '10px',
                  width: '70%',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  margin: '-4px 0 0 30px',
                }}
              />
            </div>
            <div className={styles.mainСontent}>
              <Skeleton
                variant='text'
                width={300}
                height={70}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '10px',
                  marginTop: '-13px',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '5px',
                  margin: '-8px 0',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '5px',
                  margin: '-8px 0',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '5px',
                  margin: '-8px 0',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '5px',
                  margin: '-8px 0',
                }}
              />
              <Skeleton
                variant='text'
                height={40}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '5px',
                  margin: '-8px 0',
                }}
              />
              <Skeleton
                variant='rectangular'
                height={500}
                sx={{
                  bgcolor: 'rgba(255,255,255,.1)',
                  borderRadius: '20px',
                  marginTop: '40px',
                }}
              />
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
                onChange={(event, value) => {
                  handleSearchChange(event, value);
                }}
                id='search-main'
                freeSolo
                options={htmlTags
                  .map(option => option.name)
                  .concat(cssTags.map(option => option.styleName))}
                renderInput={params => <TextField {...params} label='Поиск' />}
              />
            </Stack>

            <List>
              <ListItemButton onClick={handleOpenHtmlChange}>
                <ListItemText
                  className={styles.htmlTitle}
                  disablePadding
                  primary='HTML'
                />
                {openHtml ? <ExpandLess /> : <ExpandMore />}
              </ListItemButton>
              <Collapse
                className={styles.sidebarNavLinks}
                in={openHtml}
                timeout='auto'
                unmountOnExit>
                <React.Fragment>
                  <List component='div'>{htmlTagsData}</List>
                </React.Fragment>
              </Collapse>

              <ListItemButton onClick={handleOpenCssChange}>
                <ListItemText
                  className={styles.cssTitle}
                  disablePadding
                  primary='CSS'
                />
                {openCss ? <ExpandLess /> : <ExpandMore />}
              </ListItemButton>
              <Collapse
                className={styles.sidebarNavLinks}
                in={openCss}
                timeout='auto'
                unmountOnExit>
                <React.Fragment>
                  <List>{cssTagsData}</List>
                </React.Fragment>
              </Collapse>
            </List>
          </div>
          <div className={styles.mainСontent}>
            {htmlPage && htmlTagPage}
            {!htmlPage && cssTagPage}
          </div>
        </div>
      )}
    </>
  );
};
export default ReferenceBook;
