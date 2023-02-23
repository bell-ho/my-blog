import AWS from 'aws-sdk';

const S3_BUCKET = 'jh-mybucket';
const REGION = 'ap-northeast-2';

AWS.config.update({
  accessKeyId: process.env.S3_ACCESS_KEY_ID,
  secretAccessKey: process.env.S3_SECRET_ACCESS_KEY,
  region: 'ap-northeast-2',
});

const uploadFileToS3 = (file) => {
  // S3에 업로드할 파일 경로와 이름
  const key = `uploads/${file.name}`;

  const myBucket = new AWS.S3({
    params: { Bucket: S3_BUCKET },
    region: REGION,
  });

  // S3 업로드 요청 생성
  const params = {
    ACL: 'public-read',
    Body: file,
    Bucket: S3_BUCKET,
    Key: key,
  };

  // S3 업로드 요청 전송
  myBucket
    .putObject(params)
    .on('httpUploadProgress', (Progress, Response) => {
      console.log(Response);
    })
    .send((err) => {
      if (err) console.log(err);
    });
};

export const uploadImages = async (files) => {
  const promises = files.map((file) => uploadFileToS3(file));
  const results = await Promise.all(promises);
  console.log(results);
  // return results.map((result) => result.Location);
};
