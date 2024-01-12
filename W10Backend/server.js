import express from "express"
import mysql from "mysql"

const app = express()
const port = 3000

const db = mysql.createConnection({
	host: 'svc.sel5.cloudtype.app',
    port: 30521,
	user: 'root',
	password: 'mysql1234',
	database: 'songdb',
})


app.get('/', (req, res) => {
  res.json({result: '스마트앱프로그래밍 백엔드'})
})

app.get('/song', (req, res) => {
    const sql = 'select * from song'
    
      db.query(sql, (err, rows) => {
          if (err) {
              res.json({result: "error"})
              return console.log(err)
          }
          res.json(rows)
      })
  })

app.listen(port, () => {
  console.log(`서버 실행됨 (port ${port})`)
})