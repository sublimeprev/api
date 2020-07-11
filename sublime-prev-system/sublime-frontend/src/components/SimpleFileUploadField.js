import React from 'react';
import { Button, Link } from '@material-ui/core';

async function base64ToBloblink(base64) {
  const b64 = await fetch(base64);
  const blob = await b64.blob();
  return window.URL.createObjectURL(blob);
}

function getBase64Type(base64) {
  return base64
    .split(',')[0]
    .replace('data:', '')
    .replace(';base64', '');
}

export async function openBase64(base64, title) {
  const myWindow = window.open(``, new Date().getTime());
  const type = getBase64Type(base64);
  const blobLink = await base64ToBloblink(base64);
  myWindow.document.write(`<title>${title}</title>`);
  myWindow.document.write(
    '<style type="text/css">body {margin: 0;overflow: hidden;border: 0;}</style>'
  );
  if (type === 'application/pdf') {
    myWindow.document.write(
      `<embed width="100%" height="100%" type="${type}" src="${blobLink}" />`
    );
  } else if (type.includes('image')) {
    myWindow.document.write(
      `<object type="${type}" data="${blobLink}#toolbar=1" />`
    );
    myWindow.document.close();
  } else {
    myWindow.location.assign(blobLink);
  }
}

const SimpleFileUpload = ({
  value: { name, base64 },
  onChange = () => {},
  label,
}) => {
  function readFileAsync(newFile) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        resolve(reader.result);
      };
      reader.onerror = reject;
      reader.readAsDataURL(newFile);
    });
  }
  const onChangeFile = async newFile => {
    const base64 = await readFileAsync(newFile);
    onChange({ name: newFile.name, base64: base64 });
  };
  async function openLocalFile() {
    openBase64(base64, name);
  }
  return (
    <div>
      <label>{label}&nbsp;</label>
      <input type="file" onChange={e => onChangeFile(e.target.files[0])} />
      <div style={{ marginTop: 4 }}>
        <span>Anexo: </span>
        {base64 && name && (
          <Button component={Link} onClick={() => openLocalFile()}>
            {name}
          </Button>
        )}
      </div>
    </div>
  );
};

export default SimpleFileUpload;
