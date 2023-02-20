import { Html, Head, Main, NextScript } from 'next/document';

export default function Document() {
  return (
    <Html lang="en">
      <Head />
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet" />
      <body>
        <Main />
        <NextScript />
        <div id={'notifications'} />
      </body>
    </Html>
  );
}
