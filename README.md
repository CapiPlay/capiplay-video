## Vídeo

<details>
<summary>Post de criação de vídeo</summary>
  
##### Mapeamento:
```ruby
/api/video/criar
```
##### Parâmetros requeridos:
```ruby
@RequestParam("titulo") String titulo,
@RequestParam("descricao") String descricao,
@RequestParam("tags") List<String> tags,
@RequestParam("categoria") String categoria,
@RequestParam("ehReels") Boolean ehReels,
@RequestParam("video") MultipartFile video,
@RequestParam("miniatura") MultipartFile miniatura
```
</details>

<details>
<summary>Get de um vídeo completo</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-completo/{uuid}
```
##### Retorno:
```ruby
{
    "tags": [
        {
            "tag": "X"
        },
        {
            "tag": "Y"
        }
    ],
    "caminhos": [
       "caminho1.jpeg",
       "caminho2.mp4"
    ],
    "categoria": {
        "id": 1,
        "categoriaString": "categoria"
    },
    "titulo": "titulo",
    "descricao": "descricao",
    "uuid": "uuid"
}
```
</details>

<details>
<summary>Get de uma lista de vídeos resumidos</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-resumido
```
#####  Parâmetros requeridos:
```ruby
@RequestParam("size") int size,
@RequestParam("page") int page
```
##### Retorno:
```ruby
{
    "content": [
        {
            "uuid": "uuid",
            "titulo": "titulo",
            "caminhos": [
                "caminho1",
                "caminho2"
            ]
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 5,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```
</details>

<details>
<summary>Get de uma lista de vídeos resumidos por categoria</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-por-categoria
```
#####  Parâmetros requeridos:
```ruby
@RequestParam("categoria") Categoria categoria,
@RequestParam("size") int size,
@RequestParam("page") int page
```
##### Retorno:
```ruby
{
    "content": [
        {
            "uuid": "uuid",
            "titulo": "titulo",
            "caminhos": [
                "caminho1",
                "caminho2"
            ]
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 5,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
```
</details>

<details>
<summary>Delete de um vídeo</summary>
  
##### Mapeamento:
```ruby
/api/video/{uuid}
```
</details>

<details>
<summary>Buscar um CapiShorts</summary>

##### Mapeamento:
```ruby
/api/video/buscar-reels
```
##### Retorno:
```ruby
{
    "tags": [
        {
            "tag": "X"
        },
        {
            "tag": "Y"
        }
    ],
    "caminhos": [
       "caminho1.jpeg",
       "caminho2.mp4"
    ],
    "categoria": {
        "id": 1,
        "categoriaString": "categoria"
    },
    "titulo": "titulo",
    "descricao": "descricao",
    "uuid": "uuid"
}
```
</details>

## Pesquisa

<details>
<summary>Get de uma lista de vídeos resumidos de acordo com a pesquisa</summary>
  
##### Mapeamento:
```ruby
/api/pesquisa/{pesquisa}
```

##### Retorno:
```ruby
[
    {
        "uuid": "uuid",
        "titulo": "titulo",
        "caminhos": [
            "caminho1.jpeg",
            "caminho2.mp4"
        ]
    }
]
```
</details>

<details>
<summary>Get histórico de pesquisa do usuário</summary>

##### Mapeamento:
```ruby
/api/usuario/historico-pesquisa
```

##### Retorno:
```ruby
[
    {
        "uuid": "uuid",
        "historico": [
            {
                "id": 2,
                "pesquisa": "ultimaPesquisa"
            },
            {
                "id": 1,
                "pesquisa": "primeiraPesquisa"
            }
        ]
    }
]
```
</details>

## Imagens

<details>

<summary>Como pegar uma imagem</summary>

Quando é feita uma requisição para vídeo, é retornado o campo _Caminhos_. Para colocar uma imagem ou um vídeo, basta pegar um link e coloca-lo na seguinte URL:
```ruby
http://localhost:8082/api/static/{caminho}
```
</details>
