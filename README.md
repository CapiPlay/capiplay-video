## Vídeo

<details>
<summary>Post de criação de vídeo</summary>
  
##### Mapeamento:
```ruby
/api/video/criar
```
##### Parâmetros/Headers:
```ruby
@ModelAttribute VideoDTO videoDTO,
@RequestHeader("usuarioId") String usuarioId
```
##### Observações:
Extensão da imagem: png </br>
Extensão do vídeo: mp4
</details>

<details>
<summary>Get de um vídeo completo</summary>
  
##### Mapeamento:
```ruby
/api/video/buscar-completo/{uuid}
```

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
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
#####  Parâmetros/Headers:
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
#####  Parâmetros/Headers:
```ruby
@ModelAttribute Categoria categoria,
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
<summary>Buscar um CapiShorts</summary>

##### Mapeamento:
```ruby
/api/video/buscar-reels
```

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
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
<summary>Filtrar Vídeos</summary>

##### Mapeamento:
```ruby
/api/video/filtro/{pesquisa}
```

##### Parâmetros/Headers:
```ruby
@ModelAttribute FiltroDTO filtroDTO,
@RequestParam("page") int page,
@RequestParam("size") int size
```

##### Retorno:
```ruby
{
    "content": [
        {
            "caminhos": [
                "caminho.png",
                "caminho.mp4"
            ],
                "duracao": 600,
                "shorts": false,
                "uuid": "uuid",
                "publicacao": "2023-08-09",
                "titulo": "titulo"
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
            "pageSize": 3,
            "paged": true,
            "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 3,
    "number": 0,
    "sort": {
        "empty": true,
    	"sorted": false,
	"unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
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

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
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

##### Parâmetros/Headers:
```ruby
@RequestHeader("usuarioId") String usuarioId
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
