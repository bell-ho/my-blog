import AWS from 'aws-sdk';

const S3_BUCKET = 'jh-mybucket';
const REGION = 'ap-northeast-2';

const uploadFileToS3 = (file) => {
  // S3에 업로드할 파일 경로와 이름
  const key = `uploads/${Date.now()}_${file.name}`;

  const params = {
    ACL: 'public-read',
    Body: file,
    Bucket: S3_BUCKET,
    CacheControl: 'max-age=0,must-revalidate,public',
    Key: key,
  };

  const upload = new AWS.S3.ManagedUpload({
    params,
  });

  const promise = upload.promise();

  return promise
    .then((data) => {
      return data;
    })
    .catch((err) => {
      return console.log('There was an error uploading your photo: ', err);
    });
};

export const uploadImages = async (files) => {
  AWS.config.update({
    accessKeyId: process.env.NEXT_PUBLIC_S3_ACCESS_KEY_ID,
    secretAccessKey: process.env.NEXT_PUBLIC_S3_SECRET_ACCESS_KEY,
    region: REGION,
  });

  const promises = files.map((file) => uploadFileToS3(file));
  const results = await Promise.all(promises);
  return results.map((result) => result.Location);
};
