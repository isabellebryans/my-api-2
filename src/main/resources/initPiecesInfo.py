# Graph initializing full chess board and all chess pieces
# This graph doesn't change
# STATIC
import rdflib
from rdflib import URIRef, Namespace, Literal
from rdflib.namespace import RDF

g = rdflib.Graph()

ns = Namespace("http://example.org/chess/")

###############################################################################################
# Initialise pieces


pieces = []

# eg piece
#
# :P13 a :Pawn;
#   a :ChessPiece;
#   :colour :white.
current_colour = URIRef(ns["white"])

for i in range(32):

    current_piece = URIRef(ns["P" + str(i+1)])
    pieces.append(current_piece)
    # define the type of piece
    match i:
        case 0 | 16:
            g.add((current_piece, RDF.type, ns.King))

        case 1 | 17:
            g.add((current_piece, RDF.type, ns.Queen))

        case 2 | 3 | 18 | 19:
            g.add((current_piece, RDF.type, ns.Bishop))

        case 4 | 5 | 20 |21:
            g.add((current_piece, RDF.type, ns.Knight))

        case 6 | 7 | 22 | 23:
            g.add((current_piece, RDF.type, ns.Rook))
        case _:
            g.add((current_piece, RDF.type, ns.Pawn))

    g.add((current_piece, RDF.type, ns.ChessPiece))
    if i == 16:
        current_colour = URIRef(ns["black"])
    g.add((current_piece, ns.colour, current_colour))





print(g.serialize(format="turtle"))
g.serialize(destination="piecesInfo.ttl")